package clipperms.collection.controller;

import clipperms.collection.dto.AddRoleToUserRequest;
import clipperms.collection.model.AppRole;
import clipperms.collection.model.AppUser;
import clipperms.collection.service.AppUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * Controller class for the user related endpoints.
 */
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/user")
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
