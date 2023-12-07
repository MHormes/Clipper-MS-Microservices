package clipperms.collection.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name="AppRole")
@Table(name = "app_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppRole {

    @Id
    private UUID id;
    @Column(name = "name")
    private String name;
}
