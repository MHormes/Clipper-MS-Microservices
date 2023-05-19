package noccures.clipperms.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import noccures.clipperms.dto.clipper.ClipperWithSeriesRequest;
import noccures.clipperms.dto.mapper.ClipperConverter;
import noccures.clipperms.dto.mapper.SeriesConverter;
import noccures.clipperms.dto.series.SeriesDTO;
import noccures.clipperms.dto.series.SeriesNoClipperRequest;
import noccures.clipperms.dto.series.SeriesWithClipperRequest;
import noccures.clipperms.exceptions.DatabaseFailedOperationException;
import noccures.clipperms.exceptions.IncorrectInputException;
import noccures.clipperms.model.Clipper;
import noccures.clipperms.model.Series;
import noccures.clipperms.service.interfaces.ISeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public ResponseEntity<SeriesDTO> addSeries(@RequestBody SeriesNoClipperRequest seriesDTO) throws IncorrectInputException, DatabaseFailedOperationException {
        var seriesToAdd = seriesConverter.convertRequestNoClipperToModel(seriesDTO);
        var addedSeriesReturn = seriesService.addSeries(seriesToAdd);
        return ResponseEntity.ok().body(seriesConverter.convertModelToRequestWithClipper(addedSeriesReturn));
    }

    @GetMapping("/{id}/available")
    public ResponseEntity<Integer> getAvailableSeriesNumber(@PathVariable(value = "id") String id) throws DatabaseFailedOperationException {
        var availableNumber = seriesService.getAvailableSeriesNumber(id);
        return ResponseEntity.ok().body(availableNumber);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeriesWithClipperRequest> getSeriesWithId(@PathVariable(value = "id") String id) throws DatabaseFailedOperationException {
        var seriesWithId = seriesService.getSeriesWithId(id);
        SeriesWithClipperRequest seriesWithClipperRequest = seriesConverter.convertModelToRequestWithClipper(seriesWithId);
        return addClippersWithSeriesToSeriesWithClipperRequest(seriesWithId, seriesWithClipperRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SeriesWithClipperRequest>> getAllSeries() {
        var seriesList = seriesService.getAllSeries();
        List<SeriesWithClipperRequest> returnList = new ArrayList<>();
        for (Series series : seriesList){
            SeriesWithClipperRequest seriesWithClipperRequest = seriesConverter.convertModelToRequestWithClipper(series);
            addClippersWithSeriesToSeriesWithClipperRequest(series, seriesWithClipperRequest);
            returnList.add(seriesWithClipperRequest);
        }
        return ResponseEntity.ok().body(returnList);
    }

    //todo refactor assign clipperWithSeriesRequest -> move out of controller
    private ResponseEntity<SeriesWithClipperRequest> addClippersWithSeriesToSeriesWithClipperRequest(Series series, SeriesWithClipperRequest seriesWithClipperRequest) {
        List<ClipperWithSeriesRequest> clipperWithSeriesRequestList = new ArrayList<>();
        for (Clipper clipper : series.getClippers()) {
            clipperWithSeriesRequestList.add(clipperConverter.convertModelToClipperWithSeriesRequest(clipper));
        }
        seriesWithClipperRequest.setClippers(clipperWithSeriesRequestList);
        return ResponseEntity.ok().body(seriesWithClipperRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<SeriesDTO> updateSeries(@RequestBody SeriesNoClipperRequest seriesDTO) throws DatabaseFailedOperationException {
        var seriesWithUpdate = seriesConverter.convertRequestNoClipperToModel(seriesDTO);
        var updatedSeriesReturn = seriesService.updateSeries(seriesWithUpdate);
        return ResponseEntity.ok().body(seriesConverter.convertModelToRequestWithClipper(updatedSeriesReturn));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteSeries(@PathVariable(value = "id") String id) throws DatabaseFailedOperationException {
        seriesService.deleteSeries(id);
        return ResponseEntity.ok().build();
    }
}
