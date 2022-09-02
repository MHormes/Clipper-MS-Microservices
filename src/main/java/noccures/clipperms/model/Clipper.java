package noccures.clipperms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Clipper")
@Table(
        name = "Clipper",
        uniqueConstraints = {
                @UniqueConstraint(name = "clipper_name_unique", columnNames = "name")
        }
)
@Getter
@Setter
public class Clipper {

    @Id
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private String id;
    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @ManyToOne
    @JoinColumn(name = "series_id")
    @JsonBackReference
    private Series seriesId;
    @Column(
            name = "series_number",
            nullable = false
    )
    private int seriesNumber;
    @Column(name = "notes")
    private String notes;
    @Column(
            name = "date_added",
            nullable = false
    )
    private LocalDateTime dateAdded;

    public Clipper() {

    }

    public Clipper(String name, Series seriesId, int seriesNumber, String notes) {
        this.name = name;
        this.seriesId = seriesId;
        this.seriesNumber = seriesNumber;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Clipper{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", seriesId=" + seriesId +
                ", seriesNumber=" + seriesNumber +
                ", notes='" + notes + '\'' +
                ", dateAdded=" + dateAdded +
                '}';
    }
}


