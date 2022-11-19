package noccures.clipperms.dto.clipper;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noccures.clipperms.dto.series.SeriesNoClipperRequest;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClipperWithSeriesRequest extends ClipperDTO {

    String id;
    String name;
    SeriesNoClipperRequest series;
    int seriesNumber;
    String createdById;
}
