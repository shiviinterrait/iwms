package com.iwms.module.role.config;

import com.iwms.module.role.entity.Role;
import com.iwms.module.role.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        createRole(
                "ADMIN",
                "Administrator with full system access"
        );

        createRole(
                "USER",
                "Normal system user"
        );

        createRole(
                "MANAGER",
                "Manager with management access"
        );
    }

    private void createRole(String roleName, String description) {

        if (!roleRepository.existsByRoleName(roleName)) {

            Role role = Role.builder()
                    .roleName(roleName)
                    .description(description)
                    .build();

            roleRepository.save(role);

            System.out.println(
                    "Role created: " + roleName
            );
        }
    }
}
