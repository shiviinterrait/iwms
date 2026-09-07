package com.iwms.module.role.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {

    private UUID id;
    private String roleName;
    private String description;
}