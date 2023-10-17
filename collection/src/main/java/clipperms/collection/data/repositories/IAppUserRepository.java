package clipperms.collection.data.repositories;

import clipperms.collection.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAppUserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByUsername(String username);
}
