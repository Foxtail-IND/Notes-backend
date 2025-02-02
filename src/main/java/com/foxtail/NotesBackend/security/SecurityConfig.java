package com.foxtail.NotesBackend.security;
import com.foxtail.NotesBackend.services.CustomOAuth2UserService;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http , CustomOAuth2UserService customOAuth2UserService) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF if using JWT or API-based authentication
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/").permitAll()  // Public routes
                        .anyRequest().authenticated()  // All other requests need authentication
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // Use custom OAuth service
                        )// Enable OAuth2 login
                        .defaultSuccessUrl("http://localhost:3000/home", true)  // Redirect after successful login
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")  // Redirect after logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")  // Clear session cookies
                );

        return http.build();
    }
}
