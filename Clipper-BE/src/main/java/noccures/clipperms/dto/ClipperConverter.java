package noccures.clipperms.dto;

import noccures.clipperms.model.Clipper;

public class ClipperConverter {

    private final SeriesConverter seriesConverter = new SeriesConverter();

    public Clipper convertDTOtoModel(ClipperDTO clipperDTO){
        return new Clipper(clipperDTO.getName(), null, clipperDTO.getSeriesNumber(), clipperDTO.getCreatedBy());
    }

    public ClipperDTO convertModelToDTO(Clipper clipper){
        return new ClipperDTO(clipper.getId().toString(), clipper.getName(), seriesConverter.convertModelToDTO(clipper.getSeriesId()), clipper.getSeriesNumber(), clipper.getCreatedBy());
    }

    public ClipperDTO convertModelNoSeriesToDTO(Clipper clipper){
        return new ClipperDTO(clipper.getId().toString(), clipper.getName(), null, clipper.getSeriesNumber(), clipper.getCreatedBy());
    }
}
