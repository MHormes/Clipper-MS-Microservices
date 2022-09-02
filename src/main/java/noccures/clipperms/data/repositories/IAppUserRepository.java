package noccures.clipperms.data.repositories;

import noccures.clipperms.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
