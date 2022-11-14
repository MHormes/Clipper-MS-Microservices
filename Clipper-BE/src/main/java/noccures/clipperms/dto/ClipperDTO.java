package noccures.clipperms.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    String createdById;

}
