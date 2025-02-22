package com.reservaki.reservaki.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.reservaki.reservaki.domain.repository.ReviewRepository;
import com.reservaki.reservaki.domain.entity.Review;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final JpaReviewRepository jpaRepository;

    @Override
    public Review save(Review review) {
        return jpaRepository.save(review);
    }

    @Override
    public Review findById(UUID id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Review> findByRestaurantId(UUID restaurantId) {
        return jpaRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public Double getAverageRatingByRestaurantId(UUID restaurantId) {
        return jpaRepository.getAverageRatingByRestaurantId(restaurantId);
    }
}