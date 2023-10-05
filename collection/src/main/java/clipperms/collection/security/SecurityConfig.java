package clipperms.collection.security;

import clipperms.collection.data.repositories.IAppUserRepository;
import clipperms.collection.security.filter.CustomAuthenticationFilter;
import clipperms.collection.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final IAppUserRepository appUserRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        //todo create CSRF configuration
        http.csrf().disable().cors().configurationSource(request -> corsConfiguration);

        //STATELESS session configuration
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //ACCESS CONTROL

        String seriesPath = SecurityConstants.SERIES_PATH;
        String clipperPath = SecurityConstants.CLIPPER_PATH;
        String admin = SecurityConstants.ADMIN;
        String superAdmin = SecurityConstants.SUPER_ADMIN;
        http.authorizeHttpRequests()
                //Allow login
                .requestMatchers("/api/login", "/api/token/refresh").permitAll()
                //Allow all get requests on clippers series and refresh endpoints
                .requestMatchers(HttpMethod.GET, clipperPath + "/**", seriesPath + "/**", "/token/refresh/**").permitAll()
                //Only allow (super) admins to create clippers
                .requestMatchers(HttpMethod.POST, clipperPath + "/**", seriesPath + "/**").hasAnyAuthority(admin, superAdmin)
                //Only allow (super) admins to update clippers
                .requestMatchers(HttpMethod.PUT, clipperPath + "/**", seriesPath + "/**").hasAnyAuthority(admin, superAdmin)
                //Only allow super admins to delete clippers
                .requestMatchers(HttpMethod.DELETE, clipperPath + "/**", seriesPath + "/**").hasAuthority(superAdmin)
                //Only allow super admin to manage users.
                .requestMatchers("/api/user/**").hasAuthority(superAdmin)
                //Allow Swagger UI
                .requestMatchers("/docs/**").permitAll()
                .requestMatchers("/v3/**").permitAll()
                .anyRequest().authenticated();

        //CUSTOM FILTERS
        http.addFilter(new CustomAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), appUserRepository));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}


