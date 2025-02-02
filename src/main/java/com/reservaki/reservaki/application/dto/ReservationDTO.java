package com.reservaki.reservaki.application.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Future;
import java.time.LocalDateTime;

@Data
public class ReservationDTO {
    private Long id;

    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

    @NotNull(message = "Customer name is required")
    private String customerName;

    @NotNull(message = "Customer email is required")
    @Email(message = "Invalid email format")
    private String customerEmail;

    private String customerPhone;

    @NotNull(message = "Reservation date is required")
    @Future(message = "Reservation date must be in the future")
    private LocalDateTime reservationDate;

    @NotNull(message = "Party size is required")
    @Min(value = 1, message = "Party size must be at least 1")
    private Integer partySize;

    private String specialRequests;
}