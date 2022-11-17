package noccures.clipperms.dto.series;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noccures.clipperms.dto.clipper.ClipperNoSeriesRequest;
import noccures.clipperms.model.Clipper;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesWithClipperRequest extends SeriesDTO {

    String id;
    String name;
    List<ClipperNoSeriesRequest> clippers;
    boolean custom;
    String createdById;

}
