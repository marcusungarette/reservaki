package com.reservaki.reservaki.domain.repository;

import com.reservaki.reservaki.domain.entity.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);
    Restaurant findById(Long id);
    void deleteById(Long id);
    List<Restaurant> findAll();
    List<Restaurant> findByCuisineType(String cuisineType);
}