package com.reservaki.reservaki.infrastructure.persistence;

import com.reservaki.reservaki.domain.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaRestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByCuisineType(String cuisineType);
}
