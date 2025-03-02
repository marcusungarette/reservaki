package com.reservaki.reservaki.application.domain.entity;

import com.reservaki.reservaki.domain.entity.Restaurant;
import com.reservaki.reservaki.domain.entity.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    private Restaurant restaurant;
    private UUID reviewId;
    private LocalDateTime createdAt;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());
        restaurant.setName("Test Restaurant");

        reviewId = UUID.randomUUID();
        createdAt = LocalDateTime.now();
    }

    private Review createStandardReview() {
        return new Review(
                reviewId,
                restaurant,
                "John Doe",
                "john@example.com",
                5,
                "Great restaurant!",
                createdAt,
                null
        );
    }

    @Test
    void testReviewCreationWithAllArgs() {
        Review review = createStandardReview();

        assertEquals(reviewId, review.getId());
        assertEquals(restaurant, review.getRestaurant());
        assertEquals("John Doe", review.getCustomerName());
        assertEquals("john@example.com", review.getCustomerEmail());
        assertEquals(5, review.getRating());
        assertEquals("Great restaurant!", review.getComment());
        assertEquals(createdAt, review.getCreatedAt());
        assertNull(review.getUpdatedAt());
    }

    @Test
    void testReviewCreationWithNoArgs() {
        Review review = new Review();

        assertNull(review.getId());
        assertNull(review.getRestaurant());
        assertNull(review.getCustomerName());
        assertNull(review.getCustomerEmail());
        assertNull(review.getRating());
        assertNull(review.getComment());
        assertNotNull(review.getCreatedAt());
        assertNull(review.getUpdatedAt());
    }

    @Test
    void testReviewSetters() {
        Review review = createReviewWithSetters();
        verifyReviewSetters(review);
    }

    private Review createReviewWithSetters() {
        Review review = new Review();
        review.setId(reviewId);
        review.setRestaurant(restaurant);
        review.setCustomerName("Jane Doe");
        review.setCustomerEmail("jane@example.com");
        review.setRating(4);
        review.setComment("Nice experience");
        review.setCreatedAt(createdAt);
        review.setUpdatedAt(LocalDateTime.now());
        return review;
    }

    private void verifyReviewSetters(Review review) {
        assertEquals(reviewId, review.getId());
        assertEquals(restaurant, review.getRestaurant());
        assertEquals("Jane Doe", review.getCustomerName());
        assertEquals("jane@example.com", review.getCustomerEmail());
        assertEquals(4, review.getRating());
        assertEquals("Nice experience", review.getComment());
        assertEquals(createdAt, review.getCreatedAt());
        assertNotNull(review.getUpdatedAt());
    }

    @Test
    void testReviewDefaultCreatedAt() {
        Review review = new Review();
        assertNotNull(review.getCreatedAt());
    }

    @Test
    void testReviewEqualsAndHashCode() {
        Review review1 = createStandardReview();
        Review review2 = createStandardReview();

        Review review3 = new Review(
                UUID.randomUUID(),
                new Restaurant(),
                "Different Name",
                "different@example.com",
                3,
                "Another comment",
                LocalDateTime.now(),
                null
        );

        assertEquals(review1, review2);
        assertNotEquals(review1, review3);

        assertEquals(review1.hashCode(), review2.hashCode());
        assertNotEquals(review1.hashCode(), review3.hashCode());
    }

    @Test
    void testReviewToString() {
        Review review = createStandardReview();

        String expectedToString = review.toString();
        assertNotNull(expectedToString);
        assertTrue(expectedToString.contains("John Doe"));
        assertTrue(expectedToString.contains(reviewId.toString()));
    }
}