package com.reservaki.reservaki.domain.repository;

import com.reservaki.reservaki.domain.entity.Review;
import java.util.List;
import java.util.UUID;

public interface ReviewRepository {
    Review save(Review review);
    Review findById(UUID id);
    List<Review> findByRestaurantId(UUID restaurantId);
    Double getAverageRatingByRestaurantId(UUID restaurantId);
}