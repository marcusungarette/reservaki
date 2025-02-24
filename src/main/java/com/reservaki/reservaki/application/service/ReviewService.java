package com.reservaki.reservaki.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.reservaki.reservaki.domain.entity.Review;
import com.reservaki.reservaki.domain.entity.Restaurant;
import com.reservaki.reservaki.domain.repository.ReviewRepository;
import com.reservaki.reservaki.domain.repository.RestaurantRepository;
import com.reservaki.reservaki.application.dto.ReviewDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Review createReview(ReviewDTO dto) {
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId());
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant not found");
        }

        Review review = new Review();
        review.setRestaurant(restaurant);
        review.setCustomerName(dto.getCustomerName());
        review.setCustomerEmail(dto.getCustomerEmail());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    public List<Review> getRestaurantReviews(UUID restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    public Double getRestaurantAverageRating(UUID restaurantId) {
        return reviewRepository.getAverageRatingByRestaurantId(restaurantId);
    }

    public Review findById(UUID id) {
        return reviewRepository.findById(id);
    }
}