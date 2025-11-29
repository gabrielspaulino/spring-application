package com.myprojects.course.entities.dto;

import jakarta.validation.constraints.PositiveOrZero;

import java.util.Map;

public record ProductDTO(
        String name,
        String description,
        String imgUrl,
        @PositiveOrZero Double price,
        Map<String, String> specifications
) {}
