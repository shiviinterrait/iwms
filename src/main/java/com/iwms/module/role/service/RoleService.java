package com.iwms.module.role.service;

import com.iwms.module.role.dto.RoleRequest;
import com.iwms.module.role.dto.RoleResponse;
import com.iwms.module.role.entity.Role;
import com.iwms.module.role.mapper.RoleMapper;
import com.iwms.module.role.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(
            RoleRepository roleRepository,
            RoleMapper roleMapper) {

        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public RoleResponse createRole(RoleRequest request) {

        if (roleRepository.existsByRoleName(request.getRoleName())) {
            throw new RuntimeException(
                    "Role already exists: " + request.getRoleName()
            );
        }

        Role role = roleMapper.toEntity(request);

        Role savedRole = roleRepository.save(role);

        return roleMapper.toResponse(savedRole);
    }

    public List<RoleResponse> getAllRoles() {

        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }
}