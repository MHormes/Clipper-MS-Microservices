package clipperms.collection.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollectedClipper {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "clipper_id",
            nullable = false,
            updatable = false)
    @JsonBackReference
    private Clipper clipperId;

    private UUID userId;

    @Column(name = "notes")
    private String notes;

    @Column(name = "date_added",
            nullable = false,
            updatable = false)
    private LocalDate dateAdded;

    @Column(name = "location_bought")
    private String locationBought;

}
