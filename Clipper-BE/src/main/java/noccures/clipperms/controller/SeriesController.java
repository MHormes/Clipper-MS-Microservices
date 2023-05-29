package noccures.clipperms.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import noccures.clipperms.dto.clipper.ClipperWithSeriesResponse;
import noccures.clipperms.dto.mapper.ClipperConverter;
import noccures.clipperms.dto.mapper.SeriesConverter;
import noccures.clipperms.dto.series.SeriesCreateRequest;
import noccures.clipperms.dto.series.SeriesDTO;
import noccures.clipperms.dto.series.SeriesWithClipperResponse;
import noccures.clipperms.exceptions.DatabaseFailedOperationException;
import noccures.clipperms.exceptions.IncorrectInputException;
import noccures.clipperms.model.Clipper;
import noccures.clipperms.model.Series;
import noccures.clipperms.service.interfaces.ISeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/series")
public class SeriesController {

    private final ISeriesService seriesService;

    private final SeriesConverter seriesConverter;

    private final ClipperConverter clipperConverter;


    @Autowired
    public SeriesController(ISeriesService seriesService, SeriesConverter seriesConverter, ClipperConverter clipperConverter) {
        this.seriesService = seriesService;
        this.seriesConverter = seriesConverter;
        this.clipperConverter = clipperConverter;
    }

    @PostMapping(value = "/add",  consumes = {"multipart/form-data"})
    public ResponseEntity<SeriesDTO> addSeries(
            @RequestPart ("series") SeriesCreateRequest seriesDTO,
            @NotNull @RequestPart ("image") MultipartFile file) throws IncorrectInputException, DatabaseFailedOperationException, IOException {
        var seriesToAdd = seriesConverter.convertCreateRequestNoClipperToModel(seriesDTO, file);
        var addedSeriesReturn = seriesService.addSeries(seriesToAdd);
        return ResponseEntity.ok().body(seriesConverter.convertModelToResponseWithClipper(addedSeriesReturn));
    }

    @GetMapping("/{id}/available")
    public ResponseEntity<List<Integer>> getAvailableSeriesNumber(@PathVariable(value = "id") String id) throws DatabaseFailedOperationException {
        var availableNumber = seriesService.getAvailableSeriesNumber(id);
        return ResponseEntity.ok().body(availableNumber);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeriesWithClipperResponse> getSeriesWithId(@PathVariable(value = "id") String id) throws DatabaseFailedOperationException {
        var seriesWithId = seriesService.getSeriesWithId(id);
        SeriesWithClipperResponse seriesWithClipperResponse = seriesConverter.convertModelToResponseWithClipper(seriesWithId);
        return addClippersWithSeriesToSeriesWithClipperResponse(seriesWithId, seriesWithClipperResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SeriesWithClipperResponse>> getAllSeries() {
        var seriesList = seriesService.getAllSeries();
        List<SeriesWithClipperResponse> returnList = new ArrayList<>();
        for (Series series : seriesList){
            SeriesWithClipperResponse seriesWithClipperResponse = seriesConverter.convertModelToResponseWithClipper(series);
            addClippersWithSeriesToSeriesWithClipperResponse(series, seriesWithClipperResponse);
            returnList.add(seriesWithClipperResponse);
        }
        return ResponseEntity.ok().body(returnList);
    }

    //todo refactor assign clipperWithSeriesRequest -> move out of controller
    private ResponseEntity<SeriesWithClipperResponse> addClippersWithSeriesToSeriesWithClipperResponse(Series series, SeriesWithClipperResponse seriesWithClipperResponse) {
        List<ClipperWithSeriesResponse> clipperWithSeriesResponseList = new ArrayList<>();
        for (Clipper clipper : series.getClippers()) {
            clipperWithSeriesResponseList.add(clipperConverter.convertModelToClipperWithSeriesResponse(clipper));
        }
        seriesWithClipperResponse.setClippers(clipperWithSeriesResponseList);
        return ResponseEntity.ok().body(seriesWithClipperResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<SeriesDTO> updateSeries(
            @RequestPart SeriesCreateRequest seriesDTO,
            @NotNull @RequestPart MultipartFile file) throws DatabaseFailedOperationException, IOException {
        var seriesWithUpdate = seriesConverter.convertCreateRequestNoClipperToModel(seriesDTO, file);
        var updatedSeriesReturn = seriesService.updateSeries(seriesWithUpdate);
        return ResponseEntity.ok().body(seriesConverter.convertModelToResponseWithClipper(updatedSeriesReturn));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteSeries(@PathVariable(value = "id") String id) throws DatabaseFailedOperationException {
        seriesService.deleteSeries(id);
        return ResponseEntity.ok().build();
    }
}
