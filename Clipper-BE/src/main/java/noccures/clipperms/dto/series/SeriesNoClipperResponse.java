package noccures.clipperms.dto.series;

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

    public SeriesNoClipperResponse(String id, String name, String image, boolean custom, String createdById) {
        super(id, name, custom, createdById);
        this.imageData = image;
    }
}
