package clipperms.collection.controller;

import clipperms.collection.dto.clipper.ClipperCreateRequest;
import clipperms.collection.dto.clipper.ClipperDTO;
import clipperms.collection.dto.clipper.ClipperWithSeriesResponse;
import clipperms.collection.dto.mapper.ClipperConverter;
import clipperms.collection.model.Clipper;
import clipperms.collection.service.interfaces.IClipperService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import clipperms.collection.exceptions.DatabaseFailedOperationException;
import clipperms.collection.exceptions.IncorrectInputException;
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
@RequestMapping("/clipper")
public class ClipperController {

    private final IClipperService clipperService;
    private final ClipperConverter clipperConverter;

    @Autowired
    public ClipperController(IClipperService clipperService, ClipperConverter clipperConverter) {
        this.clipperService = clipperService;
        this.clipperConverter = clipperConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClipperDTO> getClipperWithId(@PathVariable(value = "id") String id) throws IncorrectInputException {
        var clipperWithId = clipperService.getClipperWithId(id);
        if (clipperWithId.getSeries() == null) {
            return ResponseEntity.ok().body(clipperConverter.convertModelNoSeriesToClipperNoSeries(clipperWithId));
        }
        return ResponseEntity.ok().body(clipperConverter.convertModelToClipperWithSeriesResponse(clipperWithId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClipperDTO>> getAllClippers() {
        List<ClipperDTO> returnList = new ArrayList<>();
        List<Clipper> allClippers = clipperService.getAllClippers();
        for (Clipper c : allClippers) {
            if (c.getSeries() != null) {
                ClipperWithSeriesResponse clipperWithSeriesResponse = clipperConverter.convertModelToClipperWithSeriesResponse(c);
                returnList.add(clipperWithSeriesResponse);
            } else {
                returnList.add(clipperConverter.convertModelNoSeriesToClipperNoSeries(c));
            }
        }
        return ResponseEntity.ok().body(returnList);
    }

    @PostMapping("/add")
    public ResponseEntity<ClipperDTO> addClipper(
            @RequestPart ("clipper") ClipperCreateRequest clipperDTO,
            @NotNull @RequestPart ("image") MultipartFile file) throws IncorrectInputException, DatabaseFailedOperationException, IOException {
        var clipperToAdd = clipperConverter.convertClipperCreateToModel(clipperDTO, file);
        Clipper addedClipperReturn;
        if (clipperDTO.getSeriesId() != null) {
            addedClipperReturn = clipperService.addClipper(clipperToAdd, clipperDTO.getSeriesId());
            return ResponseEntity.ok().body(clipperConverter.convertModelToClipperWithSeriesResponse(addedClipperReturn));
        } else {
            addedClipperReturn = clipperService.addClipper(clipperToAdd, null);
            return ResponseEntity.ok(clipperConverter.convertModelNoSeriesToClipperNoSeries(addedClipperReturn));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClipperDTO> updateClipper(
            @PathVariable(value = "id") String id,
            @RequestPart ("clipper") ClipperCreateRequest clipperDTO,
            @NotNull @RequestPart ("image") MultipartFile imageFile) throws IncorrectInputException, DatabaseFailedOperationException, IOException {
        var clipperWithUpdate = clipperConverter.convertClipperCreateToModel(clipperDTO, imageFile);
        var updatedClipperReturn = clipperService.updateClipper(clipperWithUpdate, clipperDTO.getSeriesId());
        if (updatedClipperReturn.getSeries() == null) {
            return ResponseEntity.ok().body(clipperConverter.convertModelNoSeriesToClipperNoSeries(updatedClipperReturn));
        }
        return ResponseEntity.ok().body(clipperConverter.convertModelToClipperWithSeriesResponse(updatedClipperReturn));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteClipper(@PathVariable(value = "id") String id) throws IncorrectInputException, DatabaseFailedOperationException {
        clipperService.deleteClipper(id);
        return ResponseEntity.ok().build();
    }
}
