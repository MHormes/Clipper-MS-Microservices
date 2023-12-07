package clipperms.collection.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        http.csrf().disable().cors().configurationSource(request -> corsConfiguration);

        String seriesPath = SecurityConstants.SERIES_PATH;
        String clipperPath = SecurityConstants.CLIPPER_PATH;
        String admin = SecurityConstants.ADMIN;
        String superAdmin = SecurityConstants.SUPER_ADMIN;

        return http
                .cors(cors -> cors.configurationSource(request -> corsConfiguration))
                //disable csrf
                .csrf().disable()

                // Stateless sessions management and saving tokens in Local storage prevent CSRF
                .authorizeHttpRequests(auth ->
                        auth
                                //Allow all get requests on clippers & series endpoints
                                .requestMatchers(HttpMethod.GET, "/clipper/**", "/series/**").permitAll()
                                //Only allow (super) admins to create clippers
                                .requestMatchers(HttpMethod.POST, clipperPath + "/**", seriesPath + "/**").hasAnyAuthority(admin, superAdmin)
                                //Only allow (super) admins to update clippers
                                .requestMatchers(HttpMethod.PUT, clipperPath + "/**", seriesPath + "/**").hasAnyAuthority(admin, superAdmin)
                                //Only allow super admins to delete clippers
                                .requestMatchers(HttpMethod.DELETE, clipperPath + "/**", seriesPath + "/**").hasAuthority(superAdmin)
                                //Allow Swagger UI
                                .requestMatchers("/docs/**").permitAll()
                                .requestMatchers("/v3/**").permitAll()
                                //test urls
                                .requestMatchers("/test/**").permitAll()

                                .anyRequest().authenticated()//Every request needs to be authenticated -> deny by default
                ) // Authorize all requests by default
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//No session will be created or used by spring security
                .oauth2ResourceServer(oauth -> oauth.jwt(conv -> conv.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter()))) // assign jwt converter
                .build();
    }
}