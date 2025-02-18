package com.reservaki.reservaki.domain.repository;

import com.reservaki.reservaki.domain.entity.Restaurant;

import java.util.List;
import java.util.UUID;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);
    Restaurant findById(UUID id);
    void deleteById(UUID id);
    List<Restaurant> findAll();
    List<Restaurant> findByCuisineType(String cuisineType);
}