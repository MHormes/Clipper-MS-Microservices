package clipperms.trading.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> corsConfiguration))

                //ACCESS CONTROL
                .authorizeHttpRequests(auth -> auth
                //Allow Swagger UI
                .requestMatchers("/docs/**").permitAll()
                .requestMatchers("/v3/**").permitAll()
                //test urls
                .requestMatchers("/test/**").permitAll()

                .anyRequest().authenticated())

                .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//No session will be created or used by spring security
            .oauth2ResourceServer(oauth -> oauth.jwt(conv -> conv.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())))// assign jwt converter
        .build();
    }
}


