package com.iwms.module.auth.mapper;

import com.iwms.module.auth.dto.UserResponse;
import com.iwms.module.auth.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .active(user.getActive())
                .build();
    }
}