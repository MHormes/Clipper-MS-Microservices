package noccures.clipperms.dto.mapper;

import noccures.clipperms.dto.SeriesDTO;
import noccures.clipperms.model.Series;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SeriesConverter {

    public Series convertDTOtoModel(SeriesDTO seriesDTO){
        return new Series(UUID.fromString(seriesDTO.getId()), seriesDTO.getName(), seriesDTO.isCustom());
    }

    public SeriesDTO convertModelToDTO(Series series){
        return new SeriesDTO(series.getId().toString(), series.getName(), series.getClippers(), series.isCustom());
    }

    public List<SeriesDTO> convertModelListToDTO(List<Series> seriesList){
        List<SeriesDTO> returnList = new ArrayList<>();
        for(Series s: seriesList){
            returnList.add(convertModelToDTO(s));
        }
        return returnList;
    }
}
