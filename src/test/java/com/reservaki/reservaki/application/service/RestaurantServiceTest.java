package com.reservaki.reservaki.application.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

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
}