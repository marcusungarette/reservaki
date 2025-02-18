package com.reservaki.reservaki.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;


import java.util.UUID;

@Data
public class RestaurantDTO {
    private UUID id;


    @Schema(description = "Nome do restaurante", example = "Restaurante do Chef")
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;


    @Schema(description = "Tipo de culinária", example = "Italiana")
    private String cuisineType;

    private String openingHours;

    @Schema(description = "Capacidade do restaurante", example = "50")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
}