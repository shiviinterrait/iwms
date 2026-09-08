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
import com.iwms.module.role.entity.UserRole;
import com.iwms.module.role.repository.RoleRepository;
import com.iwms.module.role.repository.UserRoleRepository;
import com.iwms.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public AuthService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RoleRepository roleRepository,
            UserRoleRepository userRoleRepository) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
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

        // New users will get STAFF role by default
        Role staffRole = roleRepository.findByRoleName("STAFF")
                .orElseThrow(() ->
                        new RuntimeException("STAFF role not found")
                );

        // Create user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .build();

        User savedUser = userRepository.save(user);

        // Create STAFF role assignment
        UserRole userRole = UserRole.builder()
                .user(savedUser)
                .role(staffRole)
                .roleName(staffRole.getRoleName())
                .userEmail(savedUser.getEmail())
                .isAdmin(false)
                .isStaff(true)
                .isManager(false)
                .build();

        userRoleRepository.save(userRole);

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

        // Get all roles assigned to this user
        List<UserRole> userRoles =
                userRoleRepository.findByUser(user);

        List<String> roles = userRoles.stream()
                .map(UserRole::getRoleName)
                .distinct()
                .toList();

        // Generate JWT with multiple roles
        String token = jwtService.generateToken(
                user.getEmail(),
                roles
        );

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }
}