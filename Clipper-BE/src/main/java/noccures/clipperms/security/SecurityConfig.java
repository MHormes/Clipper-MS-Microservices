package noccures.clipperms.security;

import lombok.RequiredArgsConstructor;
import noccures.clipperms.security.filter.CustomAuthenticationFilter;
import noccures.clipperms.security.filter.CustomAuthorizationFilter;
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

    private final static String clipperPath = "/api/clipper";
    private final static String seriesPath = "/api/series";

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

        http.authorizeHttpRequests()
                //Allow login
                .requestMatchers("/login").permitAll()
                //Allow all get requests on clippers series and refresh endpoints
                .requestMatchers(HttpMethod.GET, clipperPath + "/**", seriesPath + "/**", "/token/refresh/**").permitAll()
                //Only allow (super) admins to create clippers
                .requestMatchers(HttpMethod.POST, clipperPath + "/**", seriesPath + "/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                //Only allow (super) admins to update clippers
                .requestMatchers(HttpMethod.PUT, clipperPath + "/**", seriesPath + "/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                //Only allow super admins to delete clippers
                .requestMatchers(HttpMethod.DELETE, clipperPath + "/**", seriesPath + "/**").hasAuthority("ROLE_SUPER_ADMIN")
                //Only allow super admin to manage users.
                .requestMatchers("/api/user/**").hasAuthority("ROLE_SUPER_ADMIN")
                //Allow Swagger UI
                .requestMatchers("/docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();

        //CUSTOM FILTERS
        http.addFilter(new CustomAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}


