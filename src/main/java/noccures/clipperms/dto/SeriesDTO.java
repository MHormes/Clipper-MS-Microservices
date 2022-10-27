package noccures.clipperms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import noccures.clipperms.model.Clipper;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesDTO {
    String id;
    String name;

    List<Clipper> clippers;
    boolean custom;


}
