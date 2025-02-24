package com.reservaki.reservaki.integration;

import com.reservaki.reservaki.application.dto.ReservationDTO;
import com.reservaki.reservaki.application.dto.RestaurantDTO;
import com.reservaki.reservaki.domain.entity.Reservation;
import com.reservaki.reservaki.domain.entity.Restaurant;
import com.reservaki.reservaki.infrastructure.persistence.JpaRestaurantRepository;
import com.reservaki.reservaki.infrastructure.persistence.JpaReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservationIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JpaReservationRepository reservationRepository;

    @Autowired
    private JpaRestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @Test
    void shouldCreateReservation() {
        RestaurantDTO restaurantRequest = new RestaurantDTO();
        restaurantRequest.setName("Test Restaurant");
        restaurantRequest.setLocation("Test Location");
        restaurantRequest.setCuisineType("Italian");
        restaurantRequest.setCapacity(50);

        ResponseEntity<Restaurant> restaurantResponse = restTemplate
                .postForEntity("/api/restaurants", restaurantRequest, Restaurant.class);

        ReservationDTO reservationRequest = new ReservationDTO();
        reservationRequest.setRestaurantId(restaurantResponse.getBody().getId());
        reservationRequest.setCustomerName("John Doe");
        reservationRequest.setCustomerEmail("john@example.com");
        reservationRequest.setCustomerPhone("123456789");
        reservationRequest.setReservationDate(LocalDateTime.now().plusDays(1));
        reservationRequest.setPartySize(4);

        ResponseEntity<Reservation> response = restTemplate
                .postForEntity("/api/reservations", reservationRequest, Reservation.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(reservationRequest.getCustomerName(), response.getBody().getCustomerName());
        assertEquals(reservationRequest.getPartySize(), response.getBody().getPartySize());
    }

    @Test
    void shouldReturn404WhenReservationNotFound() {
        UUID nonExistentId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        ResponseEntity<Reservation> response = restTemplate
                .getForEntity("/api/reservations/{id}", Reservation.class, nonExistentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}