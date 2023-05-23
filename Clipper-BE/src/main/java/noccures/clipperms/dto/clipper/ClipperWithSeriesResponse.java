package noccures.clipperms.dto.clipper;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noccures.clipperms.dto.series.SeriesNoClipperResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClipperWithSeriesResponse extends ClipperDTO {

    private SeriesNoClipperResponse series;
    private String imageData;

    public ClipperWithSeriesResponse(String id, String name, SeriesNoClipperResponse seriesNoClipperResponse, int seriesNumber, String createdById, String imageData) {
        super(id, name, seriesNumber, createdById);
        this.series = seriesNoClipperResponse;
        this.imageData = imageData;
    }
}
