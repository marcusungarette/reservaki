package com.reservaki.reservaki.application.controller;


import com.reservaki.reservaki.application.dto.ReservationDTO;
import com.reservaki.reservaki.application.dto.RestaurantDTO;
import com.reservaki.reservaki.application.service.ReservationService;
import com.reservaki.reservaki.domain.entity.Reservation;
import com.reservaki.reservaki.domain.entity.ReservationStatus;
import com.reservaki.reservaki.domain.entity.Restaurant;
import com.reservaki.reservaki.interfaces.rest.ReservationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private ReservationDTO reservationDTO;
    private Reservation reservation;
    private UUID reservationId;
    private UUID restaurantId;

    @BeforeEach
    void setUp() {
        reservationId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        reservationDTO = new ReservationDTO();
        reservationDTO.setRestaurantId(restaurantId);
        reservationDTO.setCustomerName("John Doe");
        reservationDTO.setCustomerEmail("john@example.com");
        reservationDTO.setReservationDate(LocalDateTime.now().plusDays(1));
        reservationDTO.setPartySize(2);

        reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setRestaurant(restaurant);
        reservation.setCustomerName(reservationDTO.getCustomerName());
        reservation.setStatus(ReservationStatus.CONFIRMED);
    }

    @Test
    void createReservation_ShouldReturnCreatedReservation() {
        when(reservationService.createReservation(any(ReservationDTO.class))).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationController.createReservation(reservationDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reservationId, response.getBody().getId());
        verify(reservationService).createReservation(any(ReservationDTO.class));
    }

    @Test
    void getReservation_WhenExists_ShouldReturnReservation() {
        when(reservationService.getReservation(reservationId)).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationController.getReservation(reservationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reservationId, response.getBody().getId());
        verify(reservationService).getReservation(reservationId);
    }

    @Test
    void getReservation_WhenNotFound_ShouldReturnNotFound() {
        when(reservationService.getReservation(reservationId)).thenReturn(null);

        ResponseEntity<Reservation> response = reservationController.getReservation(reservationId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(reservationService).getReservation(reservationId);
    }

    @Test
    void getRestaurantReservations_ShouldReturnListOfReservations() {
        List<Reservation> reservations = Arrays.asList(reservation);
        when(reservationService.getRestaurantReservations(restaurantId)).thenReturn(reservations);

        ResponseEntity<List<Reservation>> response = reservationController.getRestaurantReservations(restaurantId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        verify(reservationService).getRestaurantReservations(restaurantId);
    }

    @Test
    void updateStatus_ShouldReturnUpdatedReservation() {
        ReservationStatus newStatus = ReservationStatus.CANCELLED;
        reservation.setStatus(newStatus);

        when(reservationService.updateReservationStatus(reservationId, newStatus)).thenReturn(reservation);

        ResponseEntity<Reservation> response = reservationController.updateStatus(reservationId, newStatus);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(newStatus, response.getBody().getStatus());
        verify(reservationService).updateReservationStatus(reservationId, newStatus);
    }

    @Test
    void cancelReservation_ShouldReturnNoContent() {
        doNothing().when(reservationService).cancelReservation(reservationId);

        ResponseEntity<Void> response = reservationController.cancelReservation(reservationId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reservationService).cancelReservation(reservationId);
    }
}