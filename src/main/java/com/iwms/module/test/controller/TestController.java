package com.iwms.module.test.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    // Any logged-in user can access
    @GetMapping("/secure")
    public String secureTest() {
        return "JWT authentication successful!";
    }

    // Only USER can access
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userTest() {
        return "USER role authorization successful!";
    }

    // Only ADMIN can access
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminTest() {
        return "ADMIN role authorization successful!";
    }

    // Only MANAGER can access
    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public String managerTest() {
        return "MANAGER role authorization successful!";
    }
}