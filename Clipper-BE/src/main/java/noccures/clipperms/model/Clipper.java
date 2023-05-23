package noccures.clipperms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "Clipper")
@Table(
        name = "Clipper",
        uniqueConstraints = {
                @UniqueConstraint(name = "clipper_name_unique", columnNames = "name")
        }
)
@Getter
@Setter
@AllArgsConstructor
public class Clipper {

    @Id
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private UUID id;

    @NotNull
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

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonBackReference
    private AppUser createdBy;

    @Lob
    @Column(name = "image_data", length = 1000)
    private byte[] imageData;

    public Clipper() {

    }

    public Clipper(String name, Series seriesId, int seriesNumber, AppUser creator) {
        this.name = name;
        this.seriesId = seriesId;
        this.seriesNumber = seriesNumber;
        this.createdBy = creator;
    }

    @Override
    public String toString() {
        return "Clipper{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", seriesId=" + seriesId +
                ", seriesNumber=" + seriesNumber +
                '}';
    }
}


