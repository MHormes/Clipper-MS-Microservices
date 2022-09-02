package noccures.clipperms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClipperDTO {

    String id;
    String name;
    SeriesDTO seriesId;
    int seriesNumber;
    String notes;
    LocalDateTime dateAdded;

    public ClipperDTO(String id, String name, SeriesDTO seriesId, int seriesNumber, String notes, LocalDateTime dateAdded) {
        this.id = id;
        this.name = name;
        this.seriesId = seriesId;
        this.seriesNumber = seriesNumber;
        this.notes = notes;
        this.dateAdded = dateAdded;
    }

}
