package noccures.clipperms.dto.clipper;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noccures.clipperms.dto.series.SeriesNoClipperRequest;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClipperWithSeriesRequest extends ClipperDTO {

    private SeriesNoClipperRequest series;
    private byte[] imageData;

    public ClipperWithSeriesRequest(String id, String name, SeriesNoClipperRequest seriesNoClipperRequest, int seriesNumber, String createdById, byte[] imageData) {
        super(id, name, seriesNumber, createdById);
        this.series = seriesNoClipperRequest;
        this.imageData = imageData;
    }
}
