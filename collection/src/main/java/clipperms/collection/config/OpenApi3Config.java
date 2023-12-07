package clipperms.collection.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Collection module",
                version = "v1",
                description = "This is the collection module",
                contact = @Contact(url = "https://www.linkedin.com/in/maarten-hormes/", name = "Maarten Hormes")
        ))
@SecurityScheme(
        name = "Keycloak",
        type = SecuritySchemeType.OAUTH2,
        scheme = "bearer",
        bearerFormat = "jwt",
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}/protocol/openid-connect/auth",
                        tokenUrl = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}/protocol/openid-connect/token",
                        refreshUrl = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}/protocol/openid-connect/token"
                )
        )
)
public class OpenApi3Config {

}
