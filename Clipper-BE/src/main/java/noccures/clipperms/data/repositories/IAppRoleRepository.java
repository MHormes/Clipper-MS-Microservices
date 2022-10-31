package noccures.clipperms.data.repositories;

import noccures.clipperms.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAppRoleRepository extends JpaRepository<AppRole, UUID> {
    AppRole findByName(String name);
}
