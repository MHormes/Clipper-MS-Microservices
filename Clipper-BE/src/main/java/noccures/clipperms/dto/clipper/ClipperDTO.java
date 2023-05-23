package noccures.clipperms.dto.clipper;

import lombok.*;


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
