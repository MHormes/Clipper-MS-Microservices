package noccures.clipperms.dto.series;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noccures.clipperms.dto.clipper.ClipperWithSeriesResponse;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesWithClipperResponse extends SeriesDTO {
    List<ClipperWithSeriesResponse> clippers;
    String imageData;
}
