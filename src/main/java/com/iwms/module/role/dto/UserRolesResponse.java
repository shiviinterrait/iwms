package com.iwms.module.role.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesResponse {

    private UUID userId;

    private String email;

    private List<String> roles;
}