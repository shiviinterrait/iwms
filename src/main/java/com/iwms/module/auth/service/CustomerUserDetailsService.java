package com.iwms.module.auth.service;

import com.iwms.module.auth.entity.User;
import com.iwms.module.auth.repository.UserRepository;
import com.iwms.module.role.entity.UserRole;
import com.iwms.module.role.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public CustomerUserDetailsService(
            UserRepository userRepository,
            UserRoleRepository userRoleRepository) {

        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: " + email));

        // Get all roles assigned to the user
        List<UserRole> userRoles =
                userRoleRepository.findByUser(user);

        // Convert roles into Spring Security roles
        String[] roles = userRoles.stream()
                .map(UserRole::getRoleName)
                .distinct()
                .toArray(String[]::new);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(roles)
                .disabled(!user.getActive())
                .build();
    }
}