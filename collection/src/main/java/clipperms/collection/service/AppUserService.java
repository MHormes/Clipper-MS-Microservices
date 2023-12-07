package clipperms.collection.service;

import clipperms.collection.data.repositories.IAppRoleRepository;
import clipperms.collection.data.repositories.IAppUserRepository;
import clipperms.collection.model.AppRole;
import clipperms.collection.model.AppUser;
import clipperms.collection.service.interfaces.IAppUserService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for users. Class that implements the UserDetailsService interface and provides the user related functionality in the API.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService implements IAppUserService {

    private final IAppUserRepository userRepo;
    private final IAppRoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * Method to add a new user to the database
     * @param user
     * @return AppUser
     */
    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving new user {} to database", user.getUsername());
        return userRepo.save(user);
    }

    /**
     * Method to add a new role to the database
     * @param role
     * @return AppRole
     */
    @Override
    public AppRole saveRole(AppRole role) {
        var existingRole = roleRepo.findByName(role.getName());
        if(existingRole == null){
            log.info("Saving new role {} to database", role.getName());
            return roleRepo.save(role);
        }
        else{
            log.error("Role {} already exists in the database", role.getName());
            throw new EntityExistsException("Role with name: " + role.getName() + " already exists");
        }
    }

    /**
     * Method to add a role to a user
     * @param username
     * @param roleName
     */
    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = userRepo.findByUsername(username);
        var role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
        userRepo.save(user);
        log.info("Role {} added to user {}", roleName, username);
    }
}
