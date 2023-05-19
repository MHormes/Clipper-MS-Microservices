package noccures.clipperms.dto.clipper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClipperCreateRequest extends ClipperDTO{

    private String seriesId;
}
