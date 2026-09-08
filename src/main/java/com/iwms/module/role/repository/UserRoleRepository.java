package com.iwms.module.role.repository;

import com.iwms.module.auth.entity.User;
import com.iwms.module.role.entity.Role;
import com.iwms.module.role.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    List<UserRole> findByUser(User user);

    List<UserRole> findByRole(Role role);

    boolean existsByUserAndRole(User user, Role role);

    void deleteByUserAndRole(User user, Role role);
}