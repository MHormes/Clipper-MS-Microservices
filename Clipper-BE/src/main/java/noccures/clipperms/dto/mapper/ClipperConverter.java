package noccures.clipperms.dto.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noccures.clipperms.data.repositories.IAppUserRepository;
import noccures.clipperms.dto.clipper.ClipperCreateRequest;
import noccures.clipperms.dto.clipper.ClipperNoSeriesResponse;
import noccures.clipperms.dto.clipper.ClipperWithSeriesResponse;
import noccures.clipperms.model.Clipper;
import org.springframework.stereotype.Component;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

//todo improve mapper functionalities
@RequiredArgsConstructor
@Component
@Slf4j
public class ClipperConverter {

    private final IAppUserRepository appUserRepo;

    private final SeriesConverter seriesConverter;


    //Convert a clipperCreateRequest to clipper model -> assign null for series id. It gets taken from DTO in controller.create method -> service class creates references if dto.getSeriesId != null
    public Clipper convertClipperCreateToModel(ClipperCreateRequest clipperDTO, MultipartFile imageFile) throws IOException {
        log.info("Converting clipperCreateRequest to clipper model: {}", clipperDTO);
        return new Clipper((clipperDTO.getId() != null ? UUID.fromString(clipperDTO.getId()) : null), clipperDTO.getName(), null, clipperDTO.getSeriesNumber(), appUserRepo.findById(UUID.fromString(clipperDTO.getCreatedBy())).orElseThrow(() -> new EntityNotFoundException("User with supplied id not found")), imageFile.getBytes());
    }

    //convert a Clipper model to a ClipperWithSeriesRequest
    public ClipperWithSeriesResponse convertModelToClipperWithSeriesRequest(Clipper clipper){
        log.info("Converting clipper model to clipperWithSeriesRequest: {}", clipper);
        return new ClipperWithSeriesResponse(clipper.getId().toString(), clipper.getName(), seriesConverter.convertModelToResponseNoClipper(clipper.getSeriesId()), clipper.getSeriesNumber(), clipper.getCreatedBy().getId().toString(), Base64.getEncoder().encodeToString(clipper.getImageData()));
    }

    public ClipperNoSeriesResponse convertModelToClipperNoSeriesRequest(Clipper clipper){
        log.info("Converting clipper model to clipperNoSeriesRequest: {}", clipper);
        return new ClipperNoSeriesResponse(clipper.getId().toString(), clipper.getName(), clipper.getSeriesId().getName(), clipper.getSeriesNumber(), clipper.getCreatedBy().getId().toString(), Base64.getEncoder().encodeToString(clipper.getImageData()));
    }

    public ClipperNoSeriesResponse convertModelNoSeriesToClipperNoSeries(Clipper clipper){
        log.info("Converting clipper model to clipperNoSeriesRequest: {}", clipper);
        return new ClipperNoSeriesResponse(clipper.getId().toString(), clipper.getName(), null, clipper.getSeriesNumber(), clipper.getCreatedBy().getId().toString(), Base64.getEncoder().encodeToString(clipper.getImageData()));
    }
}
