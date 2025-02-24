package com.reservaki.reservaki.integration;

import com.reservaki.reservaki.application.dto.RestaurantDTO;
import com.reservaki.reservaki.domain.entity.Restaurant;
import com.reservaki.reservaki.infrastructure.persistence.JpaRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JpaRestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        restaurantRepository.deleteAll();
    }

    @Test
    void shouldCreateRestaurant() {
        RestaurantDTO request = new RestaurantDTO();
        request.setName("Test Restaurant");
        request.setLocation("Test Location");
        request.setCuisineType("Italian");
        request.setCapacity(50);
        request.setOpeningHours("10:00-22:00");

        ResponseEntity<Restaurant> response = restTemplate
                .postForEntity("/api/restaurants", request, Restaurant.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(request.getName(), response.getBody().getName());
    }

    @Test
    void shouldReturn404WhenRestaurantNotFound() {
        ResponseEntity<Restaurant> response = restTemplate
                .getForEntity("/api/restaurants/{id}", Restaurant.class, "550e8400-e29b-41d4-a716-446655440009");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}