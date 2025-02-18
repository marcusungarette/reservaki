package com.reservaki.reservaki.integration;

import com.reservaki.reservaki.application.dto.RestaurantDTO;
import com.reservaki.reservaki.domain.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")  // Importante para usar o application-test.yml
class RestaurantIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateRestaurant() {
        // Arrange
        RestaurantDTO request = new RestaurantDTO();
        request.setName("Test Restaurant");
        request.setLocation("Test Location");
        request.setCuisineType("Italian");
        request.setCapacity(50);
        request.setOpeningHours("10:00-22:00");  // Se este campo for obrigatório

        // Act
        ResponseEntity<Restaurant> response = restTemplate
                .postForEntity("/api/restaurants", request, Restaurant.class);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());  // Verifica se gerou ID
        assertEquals(request.getName(), response.getBody().getName());
        assertEquals(request.getLocation(), response.getBody().getLocation());
        assertEquals(request.getCuisineType(), response.getBody().getCuisineType());
        assertEquals(request.getCapacity(), response.getBody().getCapacity());
    }

    // Você pode adicionar mais testes
    @Test
    void shouldReturn404WhenRestaurantNotFound() {
        ResponseEntity<Restaurant> response = restTemplate
                .getForEntity("/api/restaurants/{id}", Restaurant.class, "550e8400-e29b-41d4-a716-446655440000");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}