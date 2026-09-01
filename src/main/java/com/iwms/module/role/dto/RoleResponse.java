package com.iwms.module.role.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RoleResponse {

    private Long id;
    private String roleName;
    private String description;
}
