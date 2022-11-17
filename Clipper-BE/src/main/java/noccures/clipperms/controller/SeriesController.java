package noccures.clipperms.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import noccures.clipperms.dto.mapper.SeriesConverter;
import noccures.clipperms.dto.series.SeriesDTO;
import noccures.clipperms.dto.series.SeriesNoClipperRequest;
import noccures.clipperms.dto.series.SeriesWithClipperRequest;
import noccures.clipperms.exceptions.DatabaseFailedOperationException;
import noccures.clipperms.exceptions.IncorrectInputException;
import noccures.clipperms.service.interfaces.ISeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/series")
public class SeriesController {

    private final ISeriesService seriesService;

    private final SeriesConverter seriesConverter;


    @Autowired
    public SeriesController(ISeriesService seriesService, SeriesConverter seriesConverter) {
        this.seriesService = seriesService;
        this.seriesConverter = seriesConverter;
    }

    @PostMapping("/add")
    public SeriesDTO addSeries(@RequestBody SeriesNoClipperRequest seriesDTO) throws IncorrectInputException, DatabaseFailedOperationException {
        var seriesToAdd = seriesConverter.convertRequestNoClipperToModel(seriesDTO);
        var addedSeriesReturn = seriesService.addSeries(seriesToAdd);
        return seriesConverter.convertModelToRequestWithClipper(addedSeriesReturn);
    }

    @GetMapping("/{id}/available")
    public ResponseEntity<Integer> getAvailableSeriesNumber(@PathVariable (value="id") String id) throws DatabaseFailedOperationException {
        var availableNumber = seriesService.getAvailableSeriesNumber(id);
        return ResponseEntity.ok().body(availableNumber);
    }

    @GetMapping("/{id}")
    public SeriesDTO getSeriesWithId(@PathVariable(value = "id") String id) throws DatabaseFailedOperationException {
        var seriesWithId = seriesService.getSeriesWithId(id);
        return seriesConverter.convertModelToRequestWithClipper(seriesWithId);
    }

    @GetMapping("/all")
    public List<SeriesWithClipperRequest> getAllSeries() {
        var seriesList = seriesService.getAllSeries();
        return seriesConverter.convertModelListToRequestWithClipper(seriesList);
    }

    @PutMapping("/update")
    public SeriesDTO updateSeries(@RequestBody SeriesNoClipperRequest seriesDTO) throws DatabaseFailedOperationException {
        var seriesWithUpdate = seriesConverter.convertRequestNoClipperToModel(seriesDTO);
        var updatedSeriesReturn = seriesService.updateSeries(seriesWithUpdate);
        return seriesConverter.convertModelToRequestWithClipper(updatedSeriesReturn);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteSeries(@PathVariable(value = "id") String id) throws DatabaseFailedOperationException {
        seriesService.deleteSeries(id);
        return ResponseEntity.ok().build();
    }
}
