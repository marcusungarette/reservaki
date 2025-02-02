package com.reservaki.reservaki.application.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.reservaki.reservaki.domain.entity.Restaurant;
import com.reservaki.reservaki.domain.repository.RestaurantRepository;
import com.reservaki.reservaki.application.dto.RestaurantDTO;

@SpringBootTest
class RestaurantServiceTest {

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
        restaurant.setId(1L);
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
        assertEquals(restaurant.getName(), created.getName());
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void getRestaurantById_ShouldReturnRestaurant() {
        when(restaurantRepository.findById(1L)).thenReturn(restaurant);

        Restaurant found = restaurantService.getRestaurantById(1L);

        assertNotNull(found);
        assertEquals(restaurant.getId(), found.getId());
        verify(restaurantRepository).findById(1L);
    }

    @Test
    void updateRestaurant_ShouldReturnUpdatedRestaurant() {
        when(restaurantRepository.findById(1L)).thenReturn(restaurant);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant updated = restaurantService.updateRestaurant(1L, restaurantDTO);

        assertNotNull(updated);
        assertEquals(restaurantDTO.getName(), updated.getName());
        verify(restaurantRepository).save(any(Restaurant.class));
    }
}