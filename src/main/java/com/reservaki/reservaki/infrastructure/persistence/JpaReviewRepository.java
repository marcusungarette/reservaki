// src/main/java/com/reservaki/reservaki/infrastructure/persistence/JpaReviewRepository.java
package com.reservaki.reservaki.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.reservaki.reservaki.domain.entity.Review;
import java.util.List;
import java.util.UUID;

public interface JpaReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByRestaurantId(UUID restaurantId);

    @Query("SELECT COALESCE(AVG(r.rating), 0.0) FROM Review r WHERE r.restaurant.id = :restaurantId")
    Double getAverageRatingByRestaurantId(@Param("restaurantId") UUID restaurantId);
}