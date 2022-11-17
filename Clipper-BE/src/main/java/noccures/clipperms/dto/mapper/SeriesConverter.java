package noccures.clipperms.dto.mapper;

import lombok.RequiredArgsConstructor;
import noccures.clipperms.dto.clipper.ClipperNoSeriesRequest;
import noccures.clipperms.dto.series.SeriesNoClipperRequest;
import noccures.clipperms.dto.series.SeriesWithClipperRequest;
import noccures.clipperms.model.Clipper;
import noccures.clipperms.model.Series;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SeriesConverter {

    public final ClipperConverter clipperConverter;

    public Series convertRequestNoClipperToModel(SeriesNoClipperRequest seriesDTO){
        return new Series(UUID.fromString(seriesDTO.getId()), seriesDTO.getName(), seriesDTO.isCustom());
    }

    public SeriesNoClipperRequest convertModelToRequestNoClipper(Series series){
        return new SeriesNoClipperRequest(series.getId().toString(), series.getName(), series.isCustom(), series.getCreatedBy().getId().toString());
    }

    public SeriesWithClipperRequest convertModelToRequestWithClipper(Series series){
        SeriesWithClipperRequest seriesWithClipperRequest = new SeriesWithClipperRequest();
        seriesWithClipperRequest.setId(series.getId().toString());
        seriesWithClipperRequest.setName(series.getName());
        seriesWithClipperRequest.setCustom(series.isCustom());
        seriesWithClipperRequest.setCreatedById(series.getCreatedBy().getId().toString());

        List<ClipperNoSeriesRequest> clipperNoSeriesRequestList = new ArrayList<>();
        for(Clipper c : series.getClippers()){
            clipperNoSeriesRequestList.add(clipperConverter.convertModelNoSeriesToClipperNoSeries(c));
        }
        seriesWithClipperRequest.setClippers(clipperNoSeriesRequestList);
        return seriesWithClipperRequest;
    }

    public List<SeriesWithClipperRequest> convertModelListToRequestWithClipper(List<Series> seriesList){
        List<SeriesWithClipperRequest> returnList = new ArrayList<>();
        for(Series s: seriesList){
            returnList.add(convertModelToRequestWithClipper(s));
        }
        return returnList;
    }
}
