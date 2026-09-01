package com.iwms.module.role.repository;

import com.iwms.module.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
//RoleRepository database mai role entity ke sath work karege ..