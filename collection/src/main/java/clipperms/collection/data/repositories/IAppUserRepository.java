package clipperms.collection.data.repositories;

import clipperms.collection.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAppUserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByUsername(String username);
}
