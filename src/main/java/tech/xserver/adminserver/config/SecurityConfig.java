package tech.xserver.adminserver.config;

//import com.nimbusds.jose.jwk.JWK;
//import com.nimbusds.jose.jwk.JWKSet;
//import com.nimbusds.jose.jwk.RSAKey;
//import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
//import com.nimbusds.jose.jwk.source.JWKSource;
//import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import tech.xserver.adminserver.filter.CustomAuthenticationFilter;
import tech.xserver.adminserver.filter.CustomAuthorizationFilter;
import tech.xserver.adminserver.service.TokenService;

import java.util.List;

/**
 *
 * https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationManagerBuilder authManagerBuilder;
    private final TokenService tokenService;



// https://stackoverflow.com/questions/73378676/spring-boot-custom-authentication-filter-without-using-the-websecurityconfigur
    public SecurityConfig(
            AuthenticationManagerBuilder authManagerBuilder,
            TokenService tokenService
    ) {
        this.authManagerBuilder = authManagerBuilder;
        this.tokenService = tokenService;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthFilter = new CustomAuthenticationFilter(authManagerBuilder.getOrBuild(), tokenService);
        customAuthFilter.setFilterProcessesUrl("/auth/login");
                http.cors(cors -> {
                           // allow all origins to support the frontend
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(List.of("*"));
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                            config.setAllowedHeaders(List.of("*"));
                            cors.configurationSource(request -> config);
                        })
                        .csrf(AbstractHttpConfigurer::disable)
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(auth ->
                                auth.requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/auth/refresh").permitAll()
                                        .requestMatchers("/roles/**").hasAnyAuthority("ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/movies").hasAnyAuthority("ADMIN")
                                        // allow all requests to / movies for admins and users
                                        .requestMatchers("/movies/**").hasAnyAuthority("ADMIN", "USER", "CHILD")
                                        .requestMatchers("/votes/**").hasAnyAuthority("ADMIN", "USER", "CHILD")
                                        .requestMatchers("/views/**").hasAnyAuthority("ADMIN", "USER", "CHILD")
                                        .requestMatchers("/users/**").hasAnyAuthority("ADMIN", "USER", "CHILD")
                                        .anyRequest().authenticated()
                        )
                        .addFilter(customAuthFilter)
                        .addFilterAfter(new CustomAuthorizationFilter(tokenService), UsernamePasswordAuthenticationFilter.class
//                        .addFilterBefore(CustomAuthFilter(), UsernamePasswordAuthenticationFilter.class)

                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
