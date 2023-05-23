package noccures.clipperms.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noccures.clipperms.data.repositories.IAppRoleRepository;
import noccures.clipperms.data.repositories.IAppUserRepository;
import noccures.clipperms.model.AppRole;
import noccures.clipperms.model.AppUser;
import noccures.clipperms.security.SecurityConstants;
import noccures.clipperms.service.interfaces.IAppUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Service for users. Class that implements the UserDetailsService interface and provides the user related functionality in the API.
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

    /**
     * Method to refresh a user's token
     ** @param request
     * @param response
     */
    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                AppUser user = getUser(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(AppRole::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getWriter(), tokens);
            } catch (Exception ex) {
                response.setHeader("error", ex.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_msg", ex.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }

    }



}
