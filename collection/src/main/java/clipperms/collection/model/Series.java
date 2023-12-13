package clipperms.collection.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Table(name = "Series", uniqueConstraints = {@UniqueConstraint(name = "series_name_unique", columnNames = "name")})
@Entity(name = "Series")
@AllArgsConstructor
public class Series {

    @Id
    @Column(name = "id",
            updatable = false,
            nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "name",
            nullable = false)
    private String name;

    @OneToMany(mappedBy = "series", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Clipper> clippers;

    @Lob
    @Column(name = "image_data", length = 1000)
    private byte[] imageData;

    @NotNull
    private UUID createdBy;

    @Column(name = "custom",
            nullable = false)
    private boolean custom;


    public Series() {

    }

    public Series(String name, boolean custom, UUID createdBy) {
        this.name = name;
        this.custom = custom;
        this.clippers = new ArrayList<>();
        this.createdBy = createdBy;
    }

    public Series(UUID id, String name, byte[] imageBytes, boolean custom, UUID createdBy) {
        this.id = id;
        this.name = name;
        this.imageData = imageBytes;
        this.custom = custom;
        this.createdBy = createdBy;
        this.clippers = new ArrayList<>();
    }

    public Series(UUID id, String name, List<Clipper> clipperList, boolean custom, UUID createdBy) {
        this.id = id;
        this.name = name;
        this.clippers = clipperList;
        this.custom = custom;
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Series{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", custom=" + custom + '}';
    }
}
