package com.reservaki.reservaki.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations", description = "API para gerenciamento de reservas de restaurantes")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @Operation(summary = "Criar uma nova reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da reserva inválidos")
    })
    public ResponseEntity<Reservation> createReservation(
            @Valid @RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(
                reservationService.createReservation(reservationDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma reserva pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    public ResponseEntity<Reservation> getReservation(
            @Parameter(description = "ID da reserva") @PathVariable UUID id) {
        Reservation reservation = reservationService.getReservation(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/restaurant/{restaurantId}")
    @Operation(summary = "Listar todas as reservas de um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reservas recuperada com sucesso")
    })
    public ResponseEntity<List<Reservation>> getRestaurantReservations(
            @Parameter(description = "ID do restaurante") @PathVariable UUID restaurantId) {
        return ResponseEntity.ok(reservationService.getRestaurantReservations(restaurantId));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar o status de uma reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status da reserva atualizado"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    public ResponseEntity<Reservation> updateStatus(
            @Parameter(description = "ID da reserva") @PathVariable UUID id,
            @Parameter(description = "Novo status da reserva") @RequestParam ReservationStatus status) {
        return ResponseEntity.ok(reservationService.updateReservationStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar uma reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva cancelada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    public ResponseEntity<Void> cancelReservation(
            @Parameter(description = "ID da reserva") @PathVariable UUID id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}