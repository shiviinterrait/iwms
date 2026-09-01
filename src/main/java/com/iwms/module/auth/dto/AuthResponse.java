package com.iwms.module.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String tokenType;
    private Long userId;
    private String username;
    private String email;
}