package noccures.clipperms.service;

import lombok.RequiredArgsConstructor;
import noccures.clipperms.data.repositories.IAppRoleRepository;
import noccures.clipperms.data.repositories.IAppUserRepository;
import noccures.clipperms.model.AppRole;
import noccures.clipperms.model.AppUser;
import noccures.clipperms.service.interfaces.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserService implements IAppUserService, UserDetailsService {

    private final IAppUserRepository userRepo;
    private final IAppRoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    //Method to load users from database for spring security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username);
        if(user == null){
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

    @Override
    public AppUser saveUser(AppUser user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public AppRole saveRole(AppRole role) {
        role.setId(UUID.randomUUID().toString());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = userRepo.findByUsername(username);
        var role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
        userRepo.save(user);
    }

    @Override
    public AppUser getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepo.findAll();
    }



}
