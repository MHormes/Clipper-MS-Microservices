package noccures.clipperms.dto.clipper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClipperNoSeriesRequest extends ClipperDTO {

    private String seriesId;
    private byte[] imageData;

    public ClipperNoSeriesRequest(String id, String name, String series, int seriesNumber, String createdById, byte[] imageData) {
        super(id, name, seriesNumber, createdById);
        this.seriesId = series;
        this.imageData = imageData;
    }
}
