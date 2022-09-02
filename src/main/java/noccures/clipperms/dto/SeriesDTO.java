package noccures.clipperms.dto;

import lombok.Getter;
import lombok.Setter;
import noccures.clipperms.model.Clipper;

import java.util.List;


@Getter
@Setter
public class SeriesDTO {
    String id;
    String name;

    List<Clipper> clippers;
    boolean custom;
    boolean complete;

    public SeriesDTO(String id, String name, List<Clipper> clipperList, boolean custom, boolean complete) {
        this.id = id;
        this.name = name;
        this.clippers = clipperList;
        this.custom = custom;
        this.complete = complete;
    }

    public SeriesDTO(){

    }

}
