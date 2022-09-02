package noccures.clipperms.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Class just holds bean of KeycloakSpringBootConfigResolver to prevent an infinite loop of 'self-calling' in config class
@Configuration
public class KeycloakConfiguration {
    @Bean
    public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}
