package com.iwms.config;

import com.iwms.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // ============================
    // SECURITY FILTER CHAIN
    // ============================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                // ============================
                // CSRF
                // ============================

                .csrf(csrf -> csrf.disable())

                // ============================
                // CORS
                // ============================

                .cors(cors -> cors.configurationSource(
                        corsConfigurationSource()
                ))

                // ============================
                // SESSION
                // ============================

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // ============================
                // AUTHORIZATION
                // ============================

                .authorizeHttpRequests(auth -> auth

                        // ============================
                        // PUBLIC APIs
                        // ============================

                        .requestMatchers(
                                "/api/v1/auth/register",
                                "/api/v1/auth/login",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()


                        // ============================
                        // WAREHOUSE
                        // ============================

                        // GET → ADMIN + MANAGER + STAFF
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/warehouses/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGER",
                                "STAFF"
                        )

                        // POST → ADMIN + MANAGER
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/warehouses/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGER"
                        )

                        // PUT → ADMIN + MANAGER
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/warehouses/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGER"
                        )

                        // DELETE → ADMIN only
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/warehouses/**"
                        ).hasRole("ADMIN")


                        // ============================
                        // PRODUCTS
                        // ============================

                        // GET → ADMIN + MANAGER + STAFF
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/products/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGER",
                                "STAFF"
                        )

                        // POST → ADMIN + MANAGER
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/products/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGER"
                        )

                        // PUT → ADMIN + MANAGER
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/products/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGER"
                        )

                        // DELETE → ADMIN only
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/products/**"
                        ).hasRole("ADMIN")


                        // ============================
                        // SUPPLIERS
                        // ============================

                        // GET → ADMIN + MANAGER
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/suppliers/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGER"
                        )

                        // POST → ADMIN + MANAGER
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/suppliers/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGER"
                        )

                        // PUT → ADMIN + MANAGER
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/suppliers/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "MANAGER"
                        )

                        // DELETE → ADMIN only
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/suppliers/**"
                        ).hasRole("ADMIN")


                        // ============================
                        // ALL OTHER APIs
                        // ============================

                        .anyRequest().authenticated()
                )

                // ============================
                // JWT FILTER
                // ============================

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }


    // ============================
    // PASSWORD ENCODER
    // ============================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // ============================
    // AUTHENTICATION MANAGER
    // ============================

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }


    // ============================
    // CORS CONFIGURATION
    // ============================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        // Angular frontend URL
        configuration.setAllowedOrigins(
                List.of("http://localhost:4200")
        );

        // Allowed HTTP methods
        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE",
                        "OPTIONS"
                )
        );

        // Allowed request headers
        configuration.setAllowedHeaders(
                List.of(
                        "Authorization",
                        "Content-Type"
                )
        );

        // Expose Authorization header
        configuration.setExposedHeaders(
                List.of("Authorization")
        );

        // Apply CORS to all endpoints
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}