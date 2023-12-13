package clipperms.collection.dto.mapper;

import clipperms.collection.dto.series.SeriesCreateRequest;
import clipperms.collection.dto.series.SeriesNoClipperResponse;
import clipperms.collection.dto.series.SeriesWithClipperResponse;
import clipperms.collection.model.Series;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Series convertCreateRequestNoClipperToModel(SeriesCreateRequest seriesDTO, MultipartFile imageFile) throws IOException {
        log.info("Converting seriesNoClipperRequest to series model: {}", seriesDTO);
        return new Series((seriesDTO.getId() != null && !seriesDTO.getId().isEmpty() ? UUID.fromString(seriesDTO.getId()) : null), seriesDTO.getName(), imageFile.getBytes(), seriesDTO.isCustom(), UUID.fromString(seriesDTO.getCreatedBy()));
    }

    public SeriesNoClipperResponse convertModelToResponseNoClipper(Series series){
        log.info("Converting series model to seriesNoClipperRequest: {}", series);
        return new SeriesNoClipperResponse(series.getId().toString(), series.getName(), Base64.getEncoder().encodeToString(series.getImageData()), series.isCustom(), series.getCreatedBy().toString());
    }

    public SeriesWithClipperResponse convertModelToResponseWithClipper(Series series){
        log.info("Converting series model to seriesWithClipperRequest: {}", series);
        SeriesWithClipperResponse seriesWithClipperResponse = new SeriesWithClipperResponse();
        seriesWithClipperResponse.setId(series.getId().toString());
        seriesWithClipperResponse.setName(series.getName());
        seriesWithClipperResponse.setImageData(Base64.getEncoder().encodeToString(series.getImageData()));
        seriesWithClipperResponse.setCustom(series.isCustom());
        seriesWithClipperResponse.setCreatedBy(series.getCreatedBy().toString());
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
