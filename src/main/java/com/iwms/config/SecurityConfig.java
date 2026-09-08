package com.iwms.config;

import com.iwms.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // ============================
                        // AUTH
                        // ============================

                        .requestMatchers(
                                "/api/v1/auth/register",
                                "/api/v1/auth/login"
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
                        // OTHER APIs
                        // ============================

                        // Everything else requires login
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}