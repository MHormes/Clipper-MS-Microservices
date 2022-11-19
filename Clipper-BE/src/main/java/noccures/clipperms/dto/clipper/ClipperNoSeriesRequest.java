package noccures.clipperms.dto.clipper;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noccures.clipperms.dto.series.SeriesDTO;

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
