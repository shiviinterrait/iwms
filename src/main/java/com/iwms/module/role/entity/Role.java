package com.iwms.module.role.entity;

import jakarta.persistence.*;//ye JPA annotation ke liye -->jpa object ko database table ke sath work
import lombok.*;//boilerplat code automatically genrate krta hai

    @Entity//db table ke sath map karega
    @Table(name = "roles")
    @Getter
    @Setter
    @NoArgsConstructor//jpa ko entity object ke sath kaam krte time no argument constructor chaiyhe
    @AllArgsConstructor//id,rolename,description
    @Builder//object create krta hai like role
    public class Role {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;//id automatically genrate kare ga

        @Column(name = "role_name", nullable = false, unique = true, length = 50)
        private String roleName;

        @Column(name = "description", length = 255)
        private String description;
    }

