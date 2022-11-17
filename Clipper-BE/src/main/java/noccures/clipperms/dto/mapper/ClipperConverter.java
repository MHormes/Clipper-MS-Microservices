package noccures.clipperms.dto.mapper;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import noccures.clipperms.data.repositories.IAppUserRepository;
import noccures.clipperms.dto.clipper.ClipperCreateRequest;
import noccures.clipperms.dto.clipper.ClipperDTO;
import noccures.clipperms.dto.clipper.ClipperNoSeriesRequest;
import noccures.clipperms.dto.clipper.ClipperWithSeriesRequest;
import noccures.clipperms.model.Clipper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

//todo improve mapper functionalities
@RequiredArgsConstructor
@Component
public class ClipperConverter {

    private final IAppUserRepository appUserRepo;


    //Convert a clipperCreateRequest to clipper model -> assign null for series id. It gets taken from DTO in controller.create method -> service class creates references if dto.getSeriesId != null
    public Clipper convertClipperCreateToModel(ClipperCreateRequest clipperDTO){
        return new Clipper(UUID.fromString(clipperDTO.getId()), clipperDTO.getName(), null, clipperDTO.getSeriesNumber(), appUserRepo.findById(UUID.fromString(clipperDTO.getCreatedById())).orElseThrow(() -> new EntityNotFoundException("User with supplied id not found")));
    }

    public Clipper convertClipperWithSeriesToModel(ClipperWithSeriesRequest clipperDTO){
        return new Clipper(UUID.fromString(clipperDTO.getId()), clipperDTO.getName(), null, clipperDTO.getSeriesNumber(), appUserRepo.findById(UUID.fromString(clipperDTO.getCreatedById())).orElseThrow(() -> new EntityNotFoundException("User with supplied id not found")));
    }


    //convert a Clipper model to a ClipperWithSeriesRequest -> assign seriesId in clipperController
    public ClipperWithSeriesRequest convertModelToClipperWithSeriesRequest(Clipper clipper){
        return new ClipperWithSeriesRequest(clipper.getId().toString(), clipper.getName(), null, clipper.getSeriesNumber(), clipper.getCreatedBy().getId().toString());
    }

    public ClipperNoSeriesRequest convertModelToClipperNoSeriesRequest(Clipper clipper){
        return new ClipperNoSeriesRequest(clipper.getId().toString(), clipper.getName(), clipper.getSeriesNumber(), clipper.getCreatedBy().getId().toString());
    }

    public ClipperNoSeriesRequest convertModelNoSeriesToClipperNoSeries(Clipper clipper){
        return new ClipperNoSeriesRequest(clipper.getId().toString(), clipper.getName(), clipper.getSeriesNumber(), clipper.getCreatedBy().getId().toString());
    }
}
