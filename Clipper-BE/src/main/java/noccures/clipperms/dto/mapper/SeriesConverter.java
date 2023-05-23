package noccures.clipperms.dto.mapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noccures.clipperms.data.repositories.IAppUserRepository;
import noccures.clipperms.dto.series.SeriesCreateRequest;
import noccures.clipperms.dto.series.SeriesNoClipperResponse;
import noccures.clipperms.dto.series.SeriesWithClipperResponse;
import noccures.clipperms.model.Series;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class SeriesConverter {

    private final IAppUserRepository appUserRepo;

    public Series convertCreateRequestNoClipperToModel(SeriesCreateRequest seriesDTO, MultipartFile imageFile) throws IOException {
        log.info("Converting seriesNoClipperRequest to series model: {}", seriesDTO);
        return new Series((seriesDTO.getId() != null && !seriesDTO.getId().equals("") ? UUID.fromString(seriesDTO.getId()) : null), seriesDTO.getName(), imageFile.getBytes(), seriesDTO.isCustom(), appUserRepo.findById(UUID.fromString(seriesDTO.getCreatedBy())).orElseThrow(() -> new EntityNotFoundException("User with supplied id not found")));
    }

    public SeriesNoClipperResponse convertModelToResponseNoClipper(Series series){
        log.info("Converting series model to seriesNoClipperRequest: {}", series);
        return new SeriesNoClipperResponse(series.getId().toString(), series.getName(), Base64.getEncoder().encodeToString(series.getImageData()), series.isCustom(), series.getCreatedBy().getId().toString());
    }

    public SeriesWithClipperResponse convertModelToResponseWithClipper(Series series){
        log.info("Converting series model to seriesWithClipperRequest: {}", series);
        SeriesWithClipperResponse seriesWithClipperResponse = new SeriesWithClipperResponse();
        seriesWithClipperResponse.setId(series.getId().toString());
        seriesWithClipperResponse.setName(series.getName());
        seriesWithClipperResponse.setImageData(Base64.getEncoder().encodeToString(series.getImageData()));
        seriesWithClipperResponse.setCustom(series.isCustom());
        seriesWithClipperResponse.setCreatedBy(series.getCreatedBy().getId().toString());
        return seriesWithClipperResponse;
    }

    public List<SeriesWithClipperResponse> convertModelListToResponseWithClipper(List<Series> seriesList){
        log.info("Converting series model list to seriesWithClipperRequest list: {}", seriesList);
        List<SeriesWithClipperResponse> returnList = new ArrayList<>();
        for(Series s: seriesList){
            returnList.add(convertModelToResponseWithClipper(s));
        }
        return returnList;
    }
}
