package com.reservaki.reservaki.application.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.reservaki.reservaki.application.dto.RestaurantDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        // Setup Restaurant primeiro
        restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);  // Importante: setar o ID do restaurante
        restaurant.setName("Test Restaurant");
        restaurant.setCapacity(48);
        restaurant.setCuisineType("Italian");

        // Setup ReservationDTO
        reservationDTO = new ReservationDTO();
        reservationDTO.setRestaurantId(RESTAURANT_ID);
        reservationDTO.setCustomerName("John Doe");
        reservationDTO.setCustomerEmail("john@example.com");
        reservationDTO.setCustomerPhone("1234567890");
        reservationDTO.setReservationDate(reservationDate);
        reservationDTO.setPartySize(4);

        // Setup Reservation por último
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
}