package clipperms.collection.controller;

import clipperms.collection.dto.clipper.ClipperCreateRequest;
import clipperms.collection.dto.clipper.ClipperDTO;
import clipperms.collection.exceptions.DatabaseFailedOperationException;
import clipperms.collection.exceptions.IncorrectInputException;
import clipperms.collection.model.Clipper;
import clipperms.collection.model.CollectedClipper;
import clipperms.collection.service.ClipperService;
import clipperms.collection.service.CollectedClipperService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@SecurityRequirement(name = "Keycloak")
@RestController
@RequestMapping("/collection")
public class CollectionController {

    private final CollectedClipperService collectedClipperService;

    private final ClipperService clipperService;

    @Autowired
    public CollectionController(CollectedClipperService collectedClipperService, ClipperService clipperService) {
        this.collectedClipperService = collectedClipperService;
        this.clipperService = clipperService;
    }

    @PostMapping("/add/{clipper}/{user}")
    public ResponseEntity<CollectedClipper> addCollectedClipper(
            @PathVariable("clipper") String clipperID,
            @PathVariable ("user") String userId) throws IncorrectInputException, DatabaseFailedOperationException {
        var clipper = clipperService.getClipperWithId(clipperID);
        var collectedClipper = new CollectedClipper(clipper, UUID.fromString(userId));
        CollectedClipper collectedClipperReturn = collectedClipperService.addCollectedClipper(collectedClipper);
        return ResponseEntity.ok().body(collectedClipperReturn);
    }

    @DeleteMapping("/delete/{clipper}/{user}")
    public ResponseEntity<String> removeCollectedClipper(
            @PathVariable("clipper") String clipperID,
            @PathVariable ("user") String userId) throws IncorrectInputException, DatabaseFailedOperationException {
        collectedClipperService.deleteCollectedClipper(clipperID, userId);
        return ResponseEntity.ok().build();
    }
}
