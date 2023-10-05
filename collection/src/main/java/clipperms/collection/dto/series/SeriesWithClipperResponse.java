package clipperms.collection.dto.series;

import clipperms.collection.dto.clipper.ClipperWithSeriesResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesWithClipperResponse extends SeriesDTO {
    List<ClipperWithSeriesResponse> clippers;
    String imageData;
}
