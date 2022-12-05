package noccures.clipperms.dto.clipper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClipperNoSeriesRequest extends ClipperDTO {

    String id;
    String name;
    String seriesId;
    int seriesNumber;
    String createdById;
}
