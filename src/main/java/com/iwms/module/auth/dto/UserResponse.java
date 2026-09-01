package com.iwms.module.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Boolean active;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
}