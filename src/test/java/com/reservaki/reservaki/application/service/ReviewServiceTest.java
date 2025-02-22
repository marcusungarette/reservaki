package com.reservaki.reservaki.application.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reservaki.reservaki.domain.entity.Review;
import com.reservaki.reservaki.domain.entity.Restaurant;
import com.reservaki.reservaki.domain.repository.ReviewRepository;
import com.reservaki.reservaki.domain.repository.RestaurantRepository;
import com.reservaki.reservaki.application.dto.ReviewDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    private static final UUID RESTAURANT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private static final UUID REVIEW_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private ReviewService reviewService;

    private ReviewDTO reviewDTO;
    private Restaurant restaurant;
    private Review review;

    @BeforeEach
    void setUp() {
        // Setup Restaurant
        restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);
        restaurant.setName("Test Restaurant");
        restaurant.setCuisineType("Italian");

        // Setup ReviewDTO
        reviewDTO = new ReviewDTO();
        reviewDTO.setRestaurantId(RESTAURANT_ID);
        reviewDTO.setCustomerName("John Doe");
        reviewDTO.setCustomerEmail("john@example.com");
        reviewDTO.setRating(5);
        reviewDTO.setComment("Excellent restaurant!");

        // Setup Review
        review = new Review();
        review.setId(REVIEW_ID);
        review.setRestaurant(restaurant);
        review.setCustomerName(reviewDTO.getCustomerName());
        review.setCustomerEmail(reviewDTO.getCustomerEmail());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createReview_ShouldCreateSuccessfully() {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(restaurant);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review created = reviewService.createReview(reviewDTO);

        assertNotNull(created);
        assertEquals(REVIEW_ID, created.getId());
        assertEquals(RESTAURANT_ID, created.getRestaurant().getId());
        assertEquals(reviewDTO.getCustomerName(), created.getCustomerName());
        assertEquals(reviewDTO.getRating(), created.getRating());
        verify(restaurantRepository).findById(RESTAURANT_ID);
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void createReview_WhenRestaurantNotFound_ShouldThrowException() {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.createReview(reviewDTO);
        });

        verify(restaurantRepository).findById(RESTAURANT_ID);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void getRestaurantReviews_ShouldReturnList() {
        List<Review> reviews = Arrays.asList(review);
        when(reviewRepository.findByRestaurantId(RESTAURANT_ID)).thenReturn(reviews);

        List<Review> result = reviewService.getRestaurantReviews(RESTAURANT_ID);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(REVIEW_ID, result.get(0).getId());
        assertEquals(reviewDTO.getRating(), result.get(0).getRating());
        verify(reviewRepository).findByRestaurantId(RESTAURANT_ID);
    }

    @Test
    void getRestaurantAverageRating_ShouldReturnAverage() {
        double expectedAverage = 4.5;
        when(reviewRepository.getAverageRatingByRestaurantId(RESTAURANT_ID)).thenReturn(expectedAverage);

        Double result = reviewService.getRestaurantAverageRating(RESTAURANT_ID);

        assertNotNull(result);
        assertEquals(expectedAverage, result);
        verify(reviewRepository).getAverageRatingByRestaurantId(RESTAURANT_ID);
    }

    @Test
    void createReview_WithInvalidRating_ShouldThrowException() {
        reviewDTO.setRating(6); // Rating maior que 5

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.createReview(reviewDTO);
        });

        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void createReview_WithNullRating_ShouldThrowException() {
        reviewDTO.setRating(null);

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.createReview(reviewDTO);
        });

        verify(reviewRepository, never()).save(any(Review.class));
    }
}