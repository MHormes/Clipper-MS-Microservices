package noccures.clipperms.dto;

import lombok.*;
import noccures.clipperms.model.AppUser;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClipperDTO {

    String id;
    String name;
    SeriesDTO seriesId;
    int seriesNumber;
    AppUser createdBy;

}
