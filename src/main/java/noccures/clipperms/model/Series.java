package noccures.clipperms.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table(
        name = "Series",
        uniqueConstraints = {
                @UniqueConstraint(name = "series_name_unique", columnNames = "name")
        }
)
@Entity(name = "Series")
public class Series {

    @Id
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private String id;

    @Column(name = "name",
            nullable = false
    )
    private String name;

    @OneToMany(mappedBy = "seriesId", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Clipper> clippers;

    @Column(
            name = "custom",
            nullable = false
    )
    private boolean custom;

    @Column(
            name = "complete",
            nullable = false
    )
    private boolean complete;


    public Series() {

    }

    public Series(String name, boolean custom, boolean complete) {
        this.name = name;
        this.custom = custom;
        this.complete = complete;
        this.clippers = new ArrayList<>();
    }

    public Series(String id, String name, boolean custom, boolean complete) {
        this.id = id;
        this.name = name;
        this.custom = custom;
        this.complete = complete;
        this.clippers = new ArrayList<>();
    }

    public Series(String id, String name, List<Clipper> clipperList, boolean custom, boolean complete) {
        this.id = id;
        this.name = name;
        this.clippers = clipperList;
        this.custom = custom;
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "Series{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", custom=" + custom +
                ", complete=" + complete +
                '}';
    }
}
