package noccures.clipperms.dto.series;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noccures.clipperms.dto.clipper.ClipperDTO;
import noccures.clipperms.model.Clipper;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesNoClipperRequest extends SeriesDTO {

    String id;
    String name;
    boolean custom;
    String createdById;
}
