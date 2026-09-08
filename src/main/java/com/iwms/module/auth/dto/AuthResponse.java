package com.iwms.module.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;

    private String tokenType;

    private UUID userId;

    private String username;

    private String email;

    private List<String> roles;
}