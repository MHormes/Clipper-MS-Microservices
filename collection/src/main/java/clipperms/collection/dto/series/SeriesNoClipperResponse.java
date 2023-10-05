package clipperms.collection.dto.series;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesNoClipperResponse extends SeriesDTO {
    String imageData;

    public SeriesNoClipperResponse(String id, String name, String image, boolean custom, String createdBy) {
        super(id, name, custom, createdBy);
        this.imageData = image;
    }
}
