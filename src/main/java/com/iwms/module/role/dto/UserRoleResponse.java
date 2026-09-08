package com.iwms.module.role.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleResponse {

    private UUID userId;

    private String email;

    private UUID roleId;

    private String roleName;

    private Boolean isAdmin;

    private Boolean isStaff;

    private Boolean isManager;
}