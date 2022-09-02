package noccures.clipperms.dto;

import noccures.clipperms.model.Series;

import java.util.ArrayList;
import java.util.List;

public class SeriesConverter {

    public Series convertDTOtoModel(SeriesDTO seriesDTO){
        return new Series(seriesDTO.getId(), seriesDTO.getName(), seriesDTO.isCustom(), seriesDTO.isComplete());
    }

    public SeriesDTO convertModelToDTO(Series series){
        return new SeriesDTO(series.getId(), series.getName(), series.getClippers(), series.isCustom(), series.isComplete());
    }

    public List<SeriesDTO> convertModelListToDTO(List<Series> seriesList){
        List<SeriesDTO> returnList = new ArrayList<>();
        for(Series s: seriesList){
            returnList.add(convertModelToDTO(s));
        }
        return returnList;
    }
}
