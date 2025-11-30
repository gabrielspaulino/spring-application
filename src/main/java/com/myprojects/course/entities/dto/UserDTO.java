package com.myprojects.course.entities.dto;

import java.util.List;

public record UserDTO (
        String username,
        String email,
        String phone,
        String password,
        boolean enabled,
        List<String> roles
) {}
