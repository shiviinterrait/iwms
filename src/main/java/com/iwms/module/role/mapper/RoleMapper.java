package com.iwms.module.role.mapper;

import com.iwms.module.role.dto.RoleRequest;
import com.iwms.module.role.dto.RoleResponse;
import com.iwms.module.role.entity.Role;
import org.springframework.stereotype.Component;
//Mapper ka simple kaam hai ek object ko doosre object mein convert karna.
@Component
public class RoleMapper {

    public Role toEntity(RoleRequest request) {
        return Role.builder()
                .roleName(request.getRoleName())
                .description(request.getDescription())
                .build();
    }

    public RoleResponse toResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .description(role.getDescription())
                .build();
    }
}
