package noccures.clipperms.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "AppUser")
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @Column(name = "id",
            updatable = false,
            nullable = false
    )
    private UUID id;

    @Column(name = "name",
            nullable = false)
    private String name;

    @Column(name = "username",
            nullable = false)
    private String username;

    @Column(name = "password",
            nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles = new ArrayList<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CollectedClipper> clippers = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Clipper> createdClippers = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Series> createdSeries = new ArrayList<>();
}
