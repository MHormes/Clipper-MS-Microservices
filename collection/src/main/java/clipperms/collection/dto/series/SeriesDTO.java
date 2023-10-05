package clipperms.collection.dto.series;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class SeriesDTO {
    String id;
    String name;
    boolean custom;
    String createdBy;
}
