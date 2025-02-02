package com.reservaki.reservaki.interfaces.rest;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.reservaki.reservaki.application.service.RestaurantService;
import com.reservaki.reservaki.application.dto.RestaurantDTO;
import com.reservaki.reservaki.domain.entity.Restaurant;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        Restaurant created = restaurantService.createRestaurant(restaurantDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/cuisine/{type}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCuisine(@PathVariable String type) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByCuisineType(type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantDTO restaurantDTO) {
        Restaurant updated = restaurantService.updateRestaurant(id, restaurantDTO);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}