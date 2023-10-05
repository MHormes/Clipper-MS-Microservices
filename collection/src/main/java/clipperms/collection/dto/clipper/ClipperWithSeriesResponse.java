package clipperms.collection.dto.clipper;


import clipperms.collection.dto.series.SeriesNoClipperResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
