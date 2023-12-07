package clipperms.collection.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Configuration
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        var resourceAccess = new HashMap<>(source.getClaim("realm_access"));
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for (int i = 0; i < ((ArrayList) resourceAccess.get("roles")).size(); i++) {
            int finalI = i;
            authorities.add((GrantedAuthority) () -> ((ArrayList) resourceAccess.get("roles")).get(finalI).toString());
        }

        return new JwtAuthenticationToken(source, authorities);
    }
}