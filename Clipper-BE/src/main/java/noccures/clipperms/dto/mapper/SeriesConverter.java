package noccures.clipperms.dto.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noccures.clipperms.dto.series.SeriesNoClipperRequest;
import noccures.clipperms.dto.series.SeriesWithClipperRequest;
import noccures.clipperms.model.Series;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class SeriesConverter {

    public Series convertRequestNoClipperToModel(SeriesNoClipperRequest seriesDTO){
        log.info("Converting seriesNoClipperRequest to series model: {}", seriesDTO);
        return new Series(UUID.fromString(seriesDTO.getId()), seriesDTO.getName(), seriesDTO.isCustom());
    }

    public SeriesNoClipperRequest convertModelToRequestNoClipper(Series series){
        log.info("Converting series model to seriesNoClipperRequest: {}", series);
        return new SeriesNoClipperRequest(series.getId().toString(), series.getName(), series.isCustom(), series.getCreatedBy().getId().toString());
    }

    public SeriesWithClipperRequest convertModelToRequestWithClipper(Series series){
        log.info("Converting series model to seriesWithClipperRequest: {}", series);
        SeriesWithClipperRequest seriesWithClipperRequest = new SeriesWithClipperRequest();
        seriesWithClipperRequest.setId(series.getId().toString());
        seriesWithClipperRequest.setName(series.getName());
        seriesWithClipperRequest.setCustom(series.isCustom());
        seriesWithClipperRequest.setCreatedById(series.getCreatedBy().getId().toString());
        return seriesWithClipperRequest;
    }

    public List<SeriesWithClipperRequest> convertModelListToRequestWithClipper(List<Series> seriesList){
        log.info("Converting series model list to seriesWithClipperRequest list: {}", seriesList);
        List<SeriesWithClipperRequest> returnList = new ArrayList<>();
        for(Series s: seriesList){
            returnList.add(convertModelToRequestWithClipper(s));
        }
        return returnList;
    }
}
