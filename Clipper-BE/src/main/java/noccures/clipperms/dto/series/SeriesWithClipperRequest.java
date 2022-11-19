package noccures.clipperms.dto.series;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noccures.clipperms.dto.clipper.ClipperWithSeriesRequest;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesWithClipperRequest extends SeriesDTO {

    String id;
    String name;
    List<ClipperWithSeriesRequest> clippers;
    boolean custom;
    String createdById;

}
