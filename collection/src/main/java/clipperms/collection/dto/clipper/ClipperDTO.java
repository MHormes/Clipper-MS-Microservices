package clipperms.collection.dto.clipper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ClipperDTO {

    private String id;
    private String name;
    private int seriesNumber;
    private String createdBy;
}
