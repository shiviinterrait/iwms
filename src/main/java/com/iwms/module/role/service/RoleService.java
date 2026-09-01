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

    public RoleService(RoleRepository roleRepository,
                       RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public RoleResponse createRole(RoleRequest request) {

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

    public RoleResponse getRoleById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Role not found with id: " + id)
                );

        return roleMapper.toResponse(role);
    }

    public RoleResponse updateRole(Long id, RoleRequest request) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Role not found with id: " + id)
                );

        role.setRoleName(request.getRoleName());
        role.setDescription(request.getDescription());

        Role updatedRole = roleRepository.save(role);

        return roleMapper.toResponse(updatedRole);
    }

    public void deleteRole(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Role not found with id: " + id)
                );

        roleRepository.delete(role);
    }
}