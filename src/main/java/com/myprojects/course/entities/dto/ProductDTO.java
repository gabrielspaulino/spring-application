package com.myprojects.course.entities.dto;

import com.myprojects.course.entities.Category;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Map;
import java.util.Set;

public record ProductDTO(
        String name,
        String description,
        String imgUrl,
        @PositiveOrZero Double price,
        Map<String, String> specifications
) {}
