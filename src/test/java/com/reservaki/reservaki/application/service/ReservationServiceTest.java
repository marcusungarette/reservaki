package com.reservaki.reservaki.application.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Arrays;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.eq;
import com.reservaki.reservaki.domain.entity.ReservationStatus;

import com.reservaki.reservaki.domain.entity.Reservation;
import com.reservaki.reservaki.domain.entity.Restaurant;
import com.reservaki.reservaki.domain.repository.ReservationRepository;
import com.reservaki.reservaki.domain.repository.RestaurantRepository;
import com.reservaki.reservaki.application.dto.ReservationDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    private static final UUID RESTAURANT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private static final UUID RESERVATION_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private ReservationService reservationService;

    private ReservationDTO reservationDTO;
    private Restaurant restaurant;
    private Reservation reservation;
    private LocalDateTime reservationDate;

    @BeforeEach
    void setUp() {
        reservationDate = LocalDateTime.now().plusDays(1);

        restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);
        restaurant.setName("Test Restaurant");
        restaurant.setCapacity(48);
        restaurant.setCuisineType("Italian");

        reservationDTO = new ReservationDTO();
        reservationDTO.setRestaurantId(RESTAURANT_ID);
        reservationDTO.setCustomerName("John Doe");
        reservationDTO.setCustomerEmail("john@example.com");
        reservationDTO.setCustomerPhone("1234567890");
        reservationDTO.setReservationDate(reservationDate);
        reservationDTO.setPartySize(4);

        reservation = new Reservation();
        reservation.setId(RESERVATION_ID);
        reservation.setRestaurant(restaurant);
        reservation.setCustomerName(reservationDTO.getCustomerName());
        reservation.setCustomerEmail(reservationDTO.getCustomerEmail());
        reservation.setCustomerPhone(reservationDTO.getCustomerPhone());
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setPartySize(reservationDTO.getPartySize());
    }

    @Test
    void createReservation_ShouldCreateSuccessfully() {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(restaurant);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation created = reservationService.createReservation(reservationDTO);

        assertNotNull(created);
        assertEquals(RESERVATION_ID, created.getId());
        assertEquals(RESTAURANT_ID, created.getRestaurant().getId());
        assertEquals(reservationDTO.getCustomerName(), created.getCustomerName());
        verify(restaurantRepository).findById(RESTAURANT_ID);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void createReservation_WhenRestaurantNotFound_ShouldThrowException() {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.createReservation(reservationDTO);
        });

        verify(restaurantRepository).findById(RESTAURANT_ID);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void getReservation_ShouldReturnReservation() {
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(reservation);

        Reservation found = reservationService.getReservation(RESERVATION_ID);

        assertNotNull(found);
        assertEquals(RESERVATION_ID, found.getId());
        assertEquals(reservationDTO.getCustomerName(), found.getCustomerName());
        verify(reservationRepository).findById(RESERVATION_ID);
    }

    @Test
    void getRestaurantReservations_ShouldReturnList() {
        List<Reservation> reservations = Arrays.asList(reservation);
        when(reservationRepository.findByRestaurantId(RESTAURANT_ID)).thenReturn(reservations);

        List<Reservation> result = reservationService.getRestaurantReservations(RESTAURANT_ID);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(RESERVATION_ID, result.get(0).getId());
        verify(reservationRepository).findByRestaurantId(RESTAURANT_ID);
    }

    @Test
    void updateReservationStatus_ShouldUpdateStatus() {
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(reservation);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation updated = reservationService.updateReservationStatus(RESERVATION_ID, ReservationStatus.CONFIRMED);

        assertNotNull(updated);
        assertEquals(ReservationStatus.CONFIRMED, updated.getStatus());
        verify(reservationRepository).findById(RESERVATION_ID);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void updateReservationStatus_WhenNotFound_ShouldThrowException() {
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.updateReservationStatus(RESERVATION_ID, ReservationStatus.CONFIRMED);
        });

        verify(reservationRepository).findById(RESERVATION_ID);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void cancelReservation_ShouldUpdateStatusToCancelled() {
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(reservation);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        reservationService.cancelReservation(RESERVATION_ID);

        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
        verify(reservationRepository).findById(RESERVATION_ID);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void createReservation_WhenCapacityExceeded_ShouldThrowException() {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(restaurant);
        when(reservationRepository.findByRestaurantIdAndDate(eq(RESTAURANT_ID), any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(reservation, reservation));

        reservationDTO.setPartySize(50);

        assertThrows(IllegalStateException.class, () -> {
            reservationService.createReservation(reservationDTO);
        });

        verify(restaurantRepository).findById(RESTAURANT_ID);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }
}