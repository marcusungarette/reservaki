package com.reservaki.reservaki.application.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

@Data
public class RestaurantDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    private String cuisineType;
    private String openingHours;

    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
}