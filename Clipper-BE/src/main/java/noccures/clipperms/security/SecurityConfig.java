package noccures.clipperms.security;

import lombok.RequiredArgsConstructor;
import noccures.clipperms.security.filter.CustomAuthenticationFilter;
import noccures.clipperms.security.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

//commenting the configuration and EnableWebSecurity annotations let the keycloak security take over. Same works the other way around
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//todo Replace deprecated adapter
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static String clipperPath = "/api/clipper";

    private static String seriesPath = "/api/series";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));


        //todo create CSRF configuration
        http.csrf().disable().cors().configurationSource(request -> corsConfiguration);
        //STATELESS session configuration
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //ACCESS CONTROL
        http.authorizeRequests()
                //Allow all get requests on clippers series and refresh endpoints
                .antMatchers(HttpMethod.GET, clipperPath + "/**", seriesPath + "/**", "/token/refresh/**").permitAll()
                //Allow login
                .antMatchers("/login").permitAll()
                //Only allow (super) admins to create clippers
                .antMatchers(HttpMethod.POST, clipperPath + "/**", seriesPath + "/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                //Only allow (super) admins to update clippers
                .antMatchers(HttpMethod.PUT, clipperPath + "/**", seriesPath + "/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                //Only allow super admins to delete clippers
                .antMatchers(HttpMethod.DELETE, clipperPath + "/**", seriesPath + "/**").hasAuthority("ROLE_SUPER_ADMIN")
                //Only allow super admin to manage users.
                .antMatchers("/api/user/**").hasAuthority("ROLE_SUPER_ADMIN")
                //Allow Swagger UI
                .antMatchers("/docs/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();

        //CUSTOM FILTERS
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
