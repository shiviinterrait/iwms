package com.iwms.module.auth.service;

import com.iwms.common.exception.DuplicateResourceException;
import com.iwms.module.auth.dto.AuthResponse;
import com.iwms.module.auth.dto.LoginRequest;
import com.iwms.module.auth.dto.RegisterRequest;
import com.iwms.module.auth.dto.UserResponse;
import com.iwms.module.auth.entity.User;
import com.iwms.module.auth.mapper.UserMapper;
import com.iwms.module.auth.repository.UserRepository;
import com.iwms.module.role.entity.Role;
import com.iwms.module.role.repository.RoleRepository;
import com.iwms.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    public AuthService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    // ============================
    // REGISTER / SIGNUP
    // ============================

    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException(
                    "Username already exists: " + request.getUsername()
            );
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists: " + request.getEmail()
            );
        }

        // Find default USER role
        Role userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() ->
                        new RuntimeException("USER role not found")
                );

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .role(userRole)
                .build();

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    // ============================
    // LOGIN
    // ============================

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: "
                                        + request.getEmail()
                        )
                );

        user.setLastLoginAt(LocalDateTime.now());

        userRepository.save(user);

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().getRoleName()
        );

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}