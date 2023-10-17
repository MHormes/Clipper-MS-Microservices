package clipperms.collection.data.repositories;

import clipperms.collection.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAppRoleRepository extends JpaRepository<AppRole, UUID> {
    AppRole findByName(String name);
}
