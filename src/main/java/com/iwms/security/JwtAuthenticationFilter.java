package com.iwms.security;

import com.iwms.module.auth.service.CustomerUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Iska kaam hai har request mein Authorization: Bearer <JWT>
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomerUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomerUserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Get Authorization header
        String authHeader = request.getHeader("Authorization");

        // If token is not present, continue request
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token
        String token = authHeader.substring(7);

        try {

            // Extract email from JWT
            String email = jwtService.extractEmail(token);

            // Check if user is not already authenticated
            if (email != null &&
                    SecurityContextHolder.getContext()
                            .getAuthentication() == null) {

                // Validate JWT
                if (jwtService.isTokenValid(token, email)) {

                    // Load user from database
                    UserDetails userDetails =
                            userDetailsService.loadUserByUsername(email);

                    // Create authenticated user
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    // Set authentication in Security Context
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (Exception e) {

            // Invalid token
            System.out.println(
                    "JWT validation failed: " + e.getMessage()
            );
        }

        // Continue request
        filterChain.doFilter(request, response);
    }
}