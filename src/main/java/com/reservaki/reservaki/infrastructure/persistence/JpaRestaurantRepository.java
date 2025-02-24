package com.reservaki.reservaki.infrastructure.persistence;

import com.reservaki.reservaki.domain.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaRestaurantRepository extends JpaRepository<Restaurant, UUID> {
    List<Restaurant> findByCuisineType(String cuisineType);
    List<Restaurant> findByLocationContainingIgnoreCase(String location);
}

