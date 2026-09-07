package com.iwms.module.role.entity;

import jakarta.persistence.*;//ye JPA annotation ke liye -->jpa object ko database table ke sath work
//import lombok.*;//boilerplat code automatically genrate krta hai
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder//object create krta hai like role
@NoArgsConstructor//jpa ko entity object ke sath kaam krte time no argument constructor chaiyhe
@AllArgsConstructor//id,rolename,description
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    //        private Long id;//id automatically genrate kare ga
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    @Column(name = "description")
    private String description;
}
