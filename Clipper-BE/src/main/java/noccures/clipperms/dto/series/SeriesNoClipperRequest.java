package noccures.clipperms.dto.series;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



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
