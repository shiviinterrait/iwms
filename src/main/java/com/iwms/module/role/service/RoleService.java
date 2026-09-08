package com.iwms.module.role.service;

import com.iwms.common.exception.DuplicateResourceException;
import com.iwms.module.auth.entity.User;
import com.iwms.module.auth.repository.UserRepository;
import com.iwms.module.role.dto.AssignRoleRequest;
import com.iwms.module.role.entity.Role;
import com.iwms.module.role.entity.UserRole;
import com.iwms.module.role.repository.RoleRepository;
import com.iwms.module.role.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import com.iwms.module.role.dto.UserRoleResponse;
import com.iwms.module.role.dto.UserRolesResponse;

import java.util.stream.Collectors;
import java.util.List;
import java.util.UUID;

@Service
public class RoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public RoleService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserRoleRepository userRoleRepository) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    // ============================
    // ASSIGN ROLE TO USER
    // ============================

    public UserRole assignRole(
            UUID userId,
            AssignRoleRequest request) {

        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with ID: " + userId
                        )
                );

        // Find role
        Role role = roleRepository.findByRoleName(
                request.getRoleName().toUpperCase()
        ).orElseThrow(() ->
                new RuntimeException(
                        "Role not found: " + request.getRoleName()
                )
        );

        // Check if role is already assigned
        if (userRoleRepository.existsByUserAndRole(user, role)) {
            throw new DuplicateResourceException(
                    "Role " + role.getRoleName()
                            + " is already assigned to user "
                            + user.getEmail()
            );
        }

        // Create user-role assignment
        UserRole userRole = UserRole.builder()
                .user(user)
                .role(role)
                .roleName(role.getRoleName())
                .userEmail(user.getEmail())
                .isAdmin("ADMIN".equals(role.getRoleName()))
                .isStaff("STAFF".equals(role.getRoleName()))
                .isManager("MANAGER".equals(role.getRoleName()))
                .build();

        return userRoleRepository.save(userRole);
    }

    // ============================
    // REMOVE ROLE FROM USER
    // ============================

    public void removeRole(
            UUID userId,
            String roleName) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with ID: " + userId
                        )
                );

        Role role = roleRepository.findByRoleName(
                roleName.toUpperCase()
        ).orElseThrow(() ->
                new RuntimeException(
                        "Role not found: " + roleName
                )
        );

        if (!userRoleRepository.existsByUserAndRole(user, role)) {
            throw new RuntimeException(
                    "Role " + role.getRoleName()
                            + " is not assigned to user "
                            + user.getEmail()
            );
        }

        userRoleRepository.deleteByUserAndRole(user, role);
    }

    public List<UserRoleResponse> getUserRoles(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with ID: " + userId
                        )
                );

        return userRoleRepository.findByUser(user)
                .stream()
                .map(userRole -> UserRoleResponse.builder()
                        .userId(userRole.getUser().getId())
                        .email(userRole.getUser().getEmail())
                        .roleId(userRole.getRole().getId())
                        .roleName(userRole.getRoleName())
                        .isAdmin(userRole.getIsAdmin())
                        .isStaff(userRole.getIsStaff())
                        .isManager(userRole.getIsManager())
                        .build()
                )
                .toList();
    }
    public List<UserRolesResponse> getAllUsersWithRoles() {

        return userRepository.findAll()
                .stream()
                .map(user -> {

                    List<String> roles = userRoleRepository.findByUser(user)
                            .stream()
                            .map(UserRole::getRoleName)
                            .distinct()
                            .toList();

                    return UserRolesResponse.builder()
                            .userId(user.getId())
                            .email(user.getEmail())
                            .roles(roles)
                            .build();
                })
                .toList();
    }

}