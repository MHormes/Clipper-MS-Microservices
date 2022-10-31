package noccures.clipperms.controller;

import noccures.clipperms.dto.SeriesConverter;
import noccures.clipperms.dto.SeriesDTO;
import noccures.clipperms.exceptions.DatabaseFailedOperationException;
import noccures.clipperms.exceptions.IncorrectInputException;
import noccures.clipperms.service.interfaces.ISeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController {

    private final ISeriesService seriesService;

    private final SeriesConverter seriesConverter = new SeriesConverter();

    @Autowired
    public SeriesController(ISeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @PostMapping("/add")
    public SeriesDTO addSeries(@RequestBody SeriesDTO seriesDTO) throws IncorrectInputException, DatabaseFailedOperationException {
        var seriesToAdd = seriesConverter.convertDTOtoModel(seriesDTO);
        var addedSeriesReturn = seriesService.addSeries(seriesToAdd);
        return seriesConverter.convertModelToDTO(addedSeriesReturn);
    }

    @GetMapping("/{id}/available")
    public ResponseEntity<Integer> getAvailableSeriesNumber(@PathVariable (value="id") String id) throws DatabaseFailedOperationException {
        var availableNumber = seriesService.getAvailableSeriesNumber(id);
        return ResponseEntity.ok().body(availableNumber);
    }

    @GetMapping("/{id}")
    public SeriesDTO getSeriesWithId(@PathVariable(value = "id") String id) throws DatabaseFailedOperationException {
        var seriesWithId = seriesService.getSeriesWithId(id);
        return seriesConverter.convertModelToDTO(seriesWithId);
    }

    @GetMapping("/all")
    public List<SeriesDTO> getAllSeries() {
        var seriesList = seriesService.getAllSeries();
        return seriesConverter.convertModelListToDTO(seriesList);
    }

    @PutMapping("/update")
    public SeriesDTO updateSeries(@RequestBody SeriesDTO seriesDTO) throws DatabaseFailedOperationException {
        var seriesWithUpdate = seriesConverter.convertDTOtoModel(seriesDTO);
        var updatedSeriesReturn = seriesService.updateSeries(seriesWithUpdate);
        return seriesConverter.convertModelToDTO(updatedSeriesReturn);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteSeries(@PathVariable(value = "id") String id) throws DatabaseFailedOperationException {
        seriesService.deleteSeries(id);
        return ResponseEntity.ok().build();
    }
}
