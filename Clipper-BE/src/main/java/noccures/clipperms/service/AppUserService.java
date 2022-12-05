package noccures.clipperms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noccures.clipperms.data.repositories.IAppRoleRepository;
import noccures.clipperms.data.repositories.IAppUserRepository;
import noccures.clipperms.model.AppRole;
import noccures.clipperms.model.AppUser;
import noccures.clipperms.service.interfaces.IAppUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class that implements the UserDetailsService interface and provides the user related functionality in the API.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService implements IAppUserService, UserDetailsService {

    private final IAppUserRepository userRepo;
    private final IAppRoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * Method to load users from the database for spring security
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username);
        log.info("User found: {}, loadUserByUsername()", user);
        if(user == null){
            log.error("User: {} not found in the database", username);
            throw new UsernameNotFoundException("User not found in database");
        }
        //Define list of authorities
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (AppRole role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        //Return spring security user
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

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

    /**
     * Method to get a specific user from the database
     * @param username
     * @return AppUser
     */
    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    /**
     * Method to get all users from the database
     * @return List<AppUser>
     */
    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }



}
