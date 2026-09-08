package com.iwms.module.role.controller;

import com.iwms.module.role.dto.AssignRoleRequest;
import com.iwms.module.role.entity.UserRole;
import com.iwms.module.role.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.iwms.module.role.dto.UserRolesResponse;
import java.util.List;
import java.util.UUID;
import com.iwms.module.role.dto.UserRoleResponse;
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // ============================
    // ASSIGN ROLE TO USER
    // ============================

    @PostMapping("/users/{userId}/assign")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRole assignRole(
            @PathVariable UUID userId,
            @Valid @RequestBody AssignRoleRequest request) {

        return roleService.assignRole(userId, request);
    }

    // ============================
    // REMOVE ROLE FROM USER
    // ============================

    @DeleteMapping("/users/{userId}/{roleName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRole(
            @PathVariable UUID userId,
            @PathVariable String roleName) {

        roleService.removeRole(userId, roleName);
    }
    @GetMapping("/users/{userId}")
    public List<UserRoleResponse> getUserRoles(
            @PathVariable UUID userId) {

        return roleService.getUserRoles(userId);
    }
    @GetMapping("/users")
    public List<UserRolesResponse> getAllUsersWithRoles() {

        return roleService.getAllUsersWithRoles();
    }
}