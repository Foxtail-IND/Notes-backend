package com.foxtail.NotesBackend.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF if using JWT or API-based authentication
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth", "/login").permitAll()  // Public routes
                        .anyRequest().authenticated()  // All other requests need authentication
                )
                .oauth2Login(oauth2 -> oauth2  // Enable OAuth2 login
                        .defaultSuccessUrl("/home", true)  // Redirect after successful login
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")  // Redirect after logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")  // Clear session cookies
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(401, "Unauthorized")
                        )
                );

        return http.build();
    }
}
