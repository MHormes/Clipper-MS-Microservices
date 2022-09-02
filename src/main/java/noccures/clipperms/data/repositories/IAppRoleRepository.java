package noccures.clipperms.data.repositories;

import noccures.clipperms.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppRoleRepository extends JpaRepository<AppRole, String> {
    AppRole findByName(String name);
}
