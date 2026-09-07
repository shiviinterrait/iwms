package com.iwms.module.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {

    private UUID id;
    private String username;
    private String email;
    private Boolean active;
}