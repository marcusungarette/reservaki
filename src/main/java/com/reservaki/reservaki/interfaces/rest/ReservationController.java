package com.reservaki.reservaki.interfaces.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.reservaki.reservaki.application.service.ReservationService;
import com.reservaki.reservaki.application.dto.ReservationDTO;
import com.reservaki.reservaki.domain.entity.Reservation;
import com.reservaki.reservaki.domain.entity.ReservationStatus;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(
                reservationService.createReservation(reservationDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservation(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Reservation>> getRestaurantReservations(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(reservationService.getRestaurantReservations(restaurantId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Reservation> updateStatus(
            @PathVariable Long id,
            @RequestParam ReservationStatus status) {
        return ResponseEntity.ok(reservationService.updateReservationStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}