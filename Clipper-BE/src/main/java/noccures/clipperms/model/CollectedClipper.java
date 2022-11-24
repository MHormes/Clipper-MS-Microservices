package noccures.clipperms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "user_id",
            nullable = false,
            updatable = false)
    @JsonBackReference
    private AppUser userId;

    @Column(name = "notes")
    private String notes;

    @Column(name = "date_added",
            nullable = false,
            updatable = false)
    private LocalDate dateAdded;

    @Column(name = "location_bought")
    private String locationBought;

}
