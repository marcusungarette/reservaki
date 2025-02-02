package com.reservaki.reservaki.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.reservaki.reservaki.domain.entity.Restaurant;
import com.reservaki.reservaki.domain.repository.RestaurantRepository;
import com.reservaki.reservaki.application.dto.RestaurantDTO;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant createRestaurant(RestaurantDTO dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setLocation(dto.getLocation());
        restaurant.setCuisineType(dto.getCuisineType());
        restaurant.setOpeningHours(dto.getOpeningHours());
        restaurant.setCapacity(dto.getCapacity());

        return restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getRestaurantsByCuisineType(String cuisineType) {
        return restaurantRepository.findByCuisineType(cuisineType);
    }

    public Restaurant updateRestaurant(Long id, RestaurantDTO dto) {
        Restaurant existingRestaurant = restaurantRepository.findById(id);
        if (existingRestaurant == null) {
            return null;
        }

        existingRestaurant.setName(dto.getName());
        existingRestaurant.setLocation(dto.getLocation());
        existingRestaurant.setCuisineType(dto.getCuisineType());
        existingRestaurant.setOpeningHours(dto.getOpeningHours());
        existingRestaurant.setCapacity(dto.getCapacity());

        return restaurantRepository.save(existingRestaurant);
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}