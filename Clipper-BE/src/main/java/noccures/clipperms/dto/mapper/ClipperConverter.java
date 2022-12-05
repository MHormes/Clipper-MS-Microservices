package noccures.clipperms.dto.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noccures.clipperms.data.repositories.IAppUserRepository;
import noccures.clipperms.dto.clipper.ClipperCreateRequest;
import noccures.clipperms.dto.clipper.ClipperNoSeriesRequest;
import noccures.clipperms.dto.clipper.ClipperWithSeriesRequest;
import noccures.clipperms.model.Clipper;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;

//todo improve mapper functionalities
@RequiredArgsConstructor
@Component
@Slf4j
public class ClipperConverter {

    private final IAppUserRepository appUserRepo;

    private final SeriesConverter seriesConverter;


    //Convert a clipperCreateRequest to clipper model -> assign null for series id. It gets taken from DTO in controller.create method -> service class creates references if dto.getSeriesId != null
    public Clipper convertClipperCreateToModel(ClipperCreateRequest clipperDTO){
        log.info("Converting clipperCreateRequest to clipper model: {}", clipperDTO);
        return new Clipper(UUID.fromString(clipperDTO.getId()), clipperDTO.getName(), null, clipperDTO.getSeriesNumber(), appUserRepo.findById(UUID.fromString(clipperDTO.getCreatedById())).orElseThrow(() -> new EntityNotFoundException("User with supplied id not found")));
    }

    public Clipper convertClipperWithSeriesToModel(ClipperWithSeriesRequest clipperDTO){
        log.info("Converting clipperWithSeriesRequest to clipper model: {}", clipperDTO);
        return new Clipper(UUID.fromString(clipperDTO.getId()), clipperDTO.getName(), null, clipperDTO.getSeriesNumber(), appUserRepo.findById(UUID.fromString(clipperDTO.getCreatedById())).orElseThrow(() -> new EntityNotFoundException("User with supplied id not found")));
    }


    //convert a Clipper model to a ClipperWithSeriesRequest
    public ClipperWithSeriesRequest convertModelToClipperWithSeriesRequest(Clipper clipper){
        log.info("Converting clipper model to clipperWithSeriesRequest: {}", clipper);
        return new ClipperWithSeriesRequest(clipper.getId().toString(), clipper.getName(), seriesConverter.convertModelToRequestNoClipper(clipper.getSeriesId()), clipper.getSeriesNumber(), clipper.getCreatedBy().getId().toString());
    }

    public ClipperNoSeriesRequest convertModelToClipperNoSeriesRequest(Clipper clipper){
        log.info("Converting clipper model to clipperNoSeriesRequest: {}", clipper);
        return new ClipperNoSeriesRequest(clipper.getId().toString(), clipper.getName(), clipper.getSeriesId().getName(), clipper.getSeriesNumber(), clipper.getCreatedBy().getId().toString());
    }

    public ClipperNoSeriesRequest convertModelNoSeriesToClipperNoSeries(Clipper clipper){
        log.info("Converting clipper model to clipperNoSeriesRequest: {}", clipper);
        return new ClipperNoSeriesRequest(clipper.getId().toString(), clipper.getName(), null, clipper.getSeriesNumber(), clipper.getCreatedBy().getId().toString());
    }
}
