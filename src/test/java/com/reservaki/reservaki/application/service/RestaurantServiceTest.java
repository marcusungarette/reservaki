package com.reservaki.reservaki.application.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Arrays;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doNothing;

import com.reservaki.reservaki.domain.entity.Restaurant;
import com.reservaki.reservaki.domain.repository.RestaurantRepository;
import com.reservaki.reservaki.application.dto.RestaurantDTO;

import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    private static final UUID RESTAURANT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private RestaurantDTO restaurantDTO;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {

        restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("Test Restaurant");
        restaurantDTO.setLocation("Test Location");
        restaurantDTO.setCuisineType("Italian");
        restaurantDTO.setCapacity(100);

        restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);
        restaurant.setName(restaurantDTO.getName());
        restaurant.setLocation(restaurantDTO.getLocation());
        restaurant.setCuisineType(restaurantDTO.getCuisineType());
        restaurant.setCapacity(restaurantDTO.getCapacity());
    }

    @Test
    void createRestaurant_ShouldReturnSavedRestaurant() {
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant created = restaurantService.createRestaurant(restaurantDTO);

        assertNotNull(created);
        assertEquals(RESTAURANT_ID, created.getId());
        assertEquals(restaurant.getName(), created.getName());
        verify(restaurantRepository).save(any(Restaurant.class));
    }
    @Test
    void getRestaurantById_ShouldReturnRestaurant() {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(restaurant);

        Restaurant found = restaurantService.getRestaurantById(RESTAURANT_ID);

        assertNotNull(found);
        assertEquals(RESTAURANT_ID, found.getId());
        assertEquals(restaurant.getName(), found.getName());
        verify(restaurantRepository).findById(RESTAURANT_ID);
    }

    @Test
    void getRestaurantById_WhenNotFound_ShouldReturnNull() {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(null);

        Restaurant found = restaurantService.getRestaurantById(RESTAURANT_ID);

        assertNull(found);
        verify(restaurantRepository).findById(RESTAURANT_ID);
    }

    @Test
    void updateRestaurant_ShouldReturnUpdatedRestaurant() {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(restaurant);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        RestaurantDTO updateDTO = new RestaurantDTO();
        updateDTO.setName("Updated Name");
        updateDTO.setLocation("Updated Location");
        updateDTO.setCuisineType("Updated Cuisine");
        updateDTO.setCapacity(200);

        Restaurant updated = restaurantService.updateRestaurant(RESTAURANT_ID, updateDTO);

        assertNotNull(updated);
        assertEquals(updateDTO.getName(), updated.getName());
        assertEquals(updateDTO.getLocation(), updated.getLocation());
        assertEquals(updateDTO.getCuisineType(), updated.getCuisineType());
        assertEquals(updateDTO.getCapacity(), updated.getCapacity());
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void updateRestaurant_WhenNotFound_ShouldReturnNull() {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(null);

        Restaurant updated = restaurantService.updateRestaurant(RESTAURANT_ID, restaurantDTO);

        assertNull(updated);
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void deleteRestaurant_ShouldCallRepository() {
        doNothing().when(restaurantRepository).deleteById(RESTAURANT_ID);

        restaurantService.deleteRestaurant(RESTAURANT_ID);

        verify(restaurantRepository).deleteById(RESTAURANT_ID);
    }

    @Test
    void getAllRestaurants_ShouldReturnList() {
        List<Restaurant> restaurants = Arrays.asList(restaurant);
        when(restaurantRepository.findAll()).thenReturn(restaurants);

        List<Restaurant> result = restaurantService.getAllRestaurants();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(restaurant.getName(), result.get(0).getName());
        verify(restaurantRepository).findAll();
    }

    @Test
    void findByCuisineType_ShouldReturnFilteredList() {
        String cuisineType = "Italian";
        List<Restaurant> restaurants = Arrays.asList(restaurant);
        when(restaurantRepository.findByCuisineType(cuisineType)).thenReturn(restaurants);

        List<Restaurant> result = restaurantService.getRestaurantsByCuisineType(cuisineType);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(cuisineType, result.get(0).getCuisineType());
        verify(restaurantRepository).findByCuisineType(cuisineType);
    }
}