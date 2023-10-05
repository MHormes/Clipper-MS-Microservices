package clipperms.collection.data.repositories;

import clipperms.collection.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAppRoleRepository extends JpaRepository<AppRole, UUID> {
    AppRole findByName(String name);
}
