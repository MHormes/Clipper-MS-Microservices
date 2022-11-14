package noccures.clipperms.dto.mapper;

import lombok.RequiredArgsConstructor;
import noccures.clipperms.data.repositories.IAppUserRepository;
import noccures.clipperms.dto.ClipperDTO;
import noccures.clipperms.model.Clipper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

//todo improve mapper functionalities
@RequiredArgsConstructor
@Component
public class ClipperConverter {

    private final IAppUserRepository appUserRepo;
    private final SeriesConverter seriesConverter = new SeriesConverter();

    public Clipper convertDTOtoModel(ClipperDTO clipperDTO){
        return new Clipper(clipperDTO.getName(), null, clipperDTO.getSeriesNumber(), appUserRepo.findById(UUID.fromString(clipperDTO.getCreatedById())).orElseThrow(() -> new EntityNotFoundException("User with supplied id not found")));
    }

    public ClipperDTO convertModelToDTO(Clipper clipper){
        return new ClipperDTO(clipper.getId().toString(), clipper.getName(), seriesConverter.convertModelToDTO(clipper.getSeriesId()), clipper.getSeriesNumber(), clipper.getCreatedBy().getId().toString());
    }

    public ClipperDTO convertModelNoSeriesToDTO(Clipper clipper){
        return new ClipperDTO(clipper.getId().toString(), clipper.getName(), null, clipper.getSeriesNumber(), clipper.getCreatedBy().getId().toString());
    }
}
