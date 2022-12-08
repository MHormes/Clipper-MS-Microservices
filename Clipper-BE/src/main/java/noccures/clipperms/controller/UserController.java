package noccures.clipperms.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import noccures.clipperms.dto.AddRoleToUserRequest;
import noccures.clipperms.model.AppRole;
import noccures.clipperms.model.AppUser;
import noccures.clipperms.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Controller class for the user related endpoints.
 */
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final AppUserService userService;

    /**
     * Endpoint to receive a list of all users.
     * @return List of AppUser
     */
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    /**
     * Endpoint to save a user to the database
     * @param user
     * @return AppUser
     */
    @PostMapping("/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    /**
     * Endpoint to save a role to the database
     * @param role
     * @return AppRole
     */
    @PostMapping("/role/save")
    public ResponseEntity<AppRole> saveRole(@RequestBody AppRole role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    /**
     * Endpoint to add a role to a user
     * @param form
     * @return ResponseEntity
     */
    @PutMapping("/role/addToUser")
    public ResponseEntity<HttpStatus> addRoleToUser(@RequestBody AddRoleToUserRequest form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to refresh access tokens
     * @param request
     * @param response
     */
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshAccessToken(request, response);
    }
}
