package noccures.clipperms.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import noccures.clipperms.dto.clipper.ClipperCreateRequest;
import noccures.clipperms.dto.clipper.ClipperWithSeriesRequest;
import noccures.clipperms.dto.mapper.ClipperConverter;
import noccures.clipperms.dto.clipper.ClipperDTO;
import noccures.clipperms.exceptions.DatabaseFailedOperationException;
import noccures.clipperms.exceptions.IncorrectInputException;
import noccures.clipperms.model.Clipper;
import noccures.clipperms.service.interfaces.IClipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/clipper")
public class ClipperController {

    private final IClipperService clipperService;
    private final ClipperConverter clipperConverter;

    @Autowired
    public ClipperController(IClipperService clipperService, ClipperConverter clipperConverter) {
        this.clipperService = clipperService;
        this.clipperConverter = clipperConverter;
    }

    @PostMapping("/add")
    public ClipperDTO addClipper(@RequestBody ClipperCreateRequest clipperDTO) throws IncorrectInputException, DatabaseFailedOperationException {
        var clipperToAdd = clipperConverter.convertClipperCreateToModel(clipperDTO);
        Clipper addedClipperReturn;
        if (clipperDTO.getSeriesId() != null) {
            addedClipperReturn = clipperService.addClipper(clipperToAdd, clipperDTO.getSeriesId());
            return clipperConverter.convertModelToClipperWithSeriesRequest(addedClipperReturn);
        } else {
            addedClipperReturn = clipperService.addClipper(clipperToAdd, null);
            return clipperConverter.convertModelNoSeriesToClipperNoSeries(addedClipperReturn);
        }
    }

    @GetMapping("/{id}")
    public ClipperDTO getClipperWithId(@PathVariable(value = "id") String id) throws IncorrectInputException {
        var clipperWithId = clipperService.getClipperWithId(id);
        if (clipperWithId.getSeriesId() == null) {
            return clipperConverter.convertModelNoSeriesToClipperNoSeries(clipperWithId);
        }
        return clipperConverter.convertModelToClipperWithSeriesRequest(clipperWithId);
    }

    @GetMapping("/all")
    public List<ClipperDTO> getAllClippers() {
        List<ClipperDTO> returnList = new ArrayList<>();
        List<Clipper> allClippers = clipperService.getAllClippers();
        for (Clipper c : allClippers) {
            if (c.getSeriesId() != null) {
                ClipperWithSeriesRequest clipperWithSeriesRequest = clipperConverter.convertModelToClipperWithSeriesRequest(c);
                returnList.add(clipperWithSeriesRequest);
            } else {
                returnList.add(clipperConverter.convertModelNoSeriesToClipperNoSeries(c));
            }
        }
        return returnList;
    }

    @PutMapping("/update/{id}")
    public ClipperDTO updateClipper(@PathVariable(value = "id") String id, @RequestBody ClipperCreateRequest clipperDTO) throws IncorrectInputException, DatabaseFailedOperationException {
        var clipperWithUpdate = clipperConverter.convertClipperCreateToModel(clipperDTO);
        var updatedClipperReturn = clipperService.updateClipper(clipperWithUpdate);
        if (updatedClipperReturn.getSeriesId() == null) {
            return clipperConverter.convertModelNoSeriesToClipperNoSeries(updatedClipperReturn);
        }
        return clipperConverter.convertModelToClipperWithSeriesRequest(updatedClipperReturn);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteClipper(@PathVariable(value = "id") String id) throws IncorrectInputException, DatabaseFailedOperationException {
        clipperService.deleteClipper(id);
        return ResponseEntity.ok().build();
    }
}
