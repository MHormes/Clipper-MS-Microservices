package noccures.clipperms.dto.clipper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClipperNoSeriesResponse extends ClipperDTO {

    private String seriesId;
    private String imageData;

    public ClipperNoSeriesResponse(String id, String name, String series, int seriesNumber, String createdById, String imageData) {
        super(id, name, seriesNumber, createdById);
        this.seriesId = series;
        this.imageData = imageData;
    }
}
