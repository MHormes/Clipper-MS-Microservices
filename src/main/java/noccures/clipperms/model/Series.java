package noccures.clipperms.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Table(name = "Series", uniqueConstraints = {@UniqueConstraint(name = "series_name_unique", columnNames = "name")})
@Entity(name = "Series")
public class Series {

    @Id
    @Column(name = "id",
            updatable = false,
            nullable = false)
    private UUID id;

    @Column(name = "name",
            nullable = false)
    private String name;

    @OneToMany(mappedBy = "seriesId")
    private List<Clipper> clippers;

    @Column(name = "custom",
            nullable = false)
    private boolean custom;


    public Series() {

    }

    public Series(String name, boolean custom) {
        this.name = name;
        this.custom = custom;
        this.clippers = new ArrayList<>();
    }

    public Series(UUID id, String name, boolean custom) {
        this.id = id;
        this.name = name;
        this.custom = custom;
        this.clippers = new ArrayList<>();
    }

    public Series(UUID id, String name, List<Clipper> clipperList, boolean custom) {
        this.id = id;
        this.name = name;
        this.clippers = clipperList;
        this.custom = custom;
    }

    @Override
    public String toString() {
        return "Series{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", custom=" + custom + '}';
    }
}
