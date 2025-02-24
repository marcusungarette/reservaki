package com.reservaki.reservaki.integration;

import com.reservaki.reservaki.application.dto.ReviewDTO;
import com.reservaki.reservaki.application.dto.RestaurantDTO;
import com.reservaki.reservaki.domain.entity.Review;
import com.reservaki.reservaki.domain.entity.Restaurant;
import com.reservaki.reservaki.infrastructure.persistence.JpaRestaurantRepository;
import com.reservaki.reservaki.infrastructure.persistence.JpaReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReviewIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JpaReviewRepository reviewRepository;

    @Autowired
    private JpaRestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
        restaurantRepository.deleteAll();
    }

    @Test
    void shouldCreateReview() {
        RestaurantDTO restaurantRequest = new RestaurantDTO();
        restaurantRequest.setName("Test Restaurant");
        restaurantRequest.setLocation("Test Location");
        restaurantRequest.setCuisineType("Italian");
        restaurantRequest.setCapacity(50);

        ResponseEntity<Restaurant> restaurantResponse = restTemplate
                .postForEntity("/api/restaurants", restaurantRequest, Restaurant.class);

        ReviewDTO reviewRequest = new ReviewDTO();
        reviewRequest.setRestaurantId(restaurantResponse.getBody().getId());
        reviewRequest.setCustomerName("John Doe");
        reviewRequest.setCustomerEmail("john@example.com");
        reviewRequest.setRating(5);
        reviewRequest.setComment("Excellent restaurant!");

        ResponseEntity<Review> response = restTemplate
                .postForEntity("/api/reviews", reviewRequest, Review.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(reviewRequest.getCustomerName(), response.getBody().getCustomerName());
    }

    @Test
    void shouldReturn404WhenReviewNotFound() {
        UUID nonExistentId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        ResponseEntity<Review> response = restTemplate
                .getForEntity("/api/reviews/{id}", Review.class, nonExistentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}