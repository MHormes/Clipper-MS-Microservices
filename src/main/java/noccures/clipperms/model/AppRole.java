package noccures.clipperms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="AppRole")
@Table(name = "app_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppRole {

    @Id
    private String id;
    @Column(name = "name")
    private String name;
}
