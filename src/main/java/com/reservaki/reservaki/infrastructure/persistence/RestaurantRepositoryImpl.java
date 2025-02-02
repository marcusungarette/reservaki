package com.reservaki.reservaki.infrastructure.persistence;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.reservaki.reservaki.domain.repository.RestaurantRepository;
import com.reservaki.reservaki.domain.entity.Restaurant;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final JpaRestaurantRepository jpaRepository;

    @Override
    public Restaurant save(Restaurant restaurant) {
        return jpaRepository.save(restaurant);
    }

    @Override
    public Restaurant findById(Long id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Restaurant> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<Restaurant> findByCuisineType(String cuisineType) {
        return jpaRepository.findByCuisineType(cuisineType);
    }
}