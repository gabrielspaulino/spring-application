package com.myprojects.course.entities.dto;

import com.myprojects.course.entities.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ReviewDTO(
        @Min(0) @Max(5) int rating,
        String comment,
        Product product
) {}
