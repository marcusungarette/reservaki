package com.reservaki.reservaki.application.controller;

import com.reservaki.reservaki.application.dto.RestaurantDTO;
import com.reservaki.reservaki.application.service.RestaurantService;
import com.reservaki.reservaki.domain.entity.Restaurant;
import com.reservaki.reservaki.interfaces.rest.RestaurantController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    private RestaurantDTO restaurantDTO;
    private Restaurant restaurant;
    private UUID restaurantId;

    @BeforeEach
    void setUp() {
        restaurantId = UUID.randomUUID();
        restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("Test Restaurant");
        restaurantDTO.setLocation("Test Location");
        restaurantDTO.setCuisineType("Italian");

        restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setName(restaurantDTO.getName());
        restaurant.setLocation(restaurantDTO.getLocation());
        restaurant.setCuisineType(restaurantDTO.getCuisineType());
    }

    @Test
    void createRestaurant_ShouldReturnCreatedRestaurant() {
        when(restaurantService.createRestaurant(any(RestaurantDTO.class))).thenReturn(restaurant);

        ResponseEntity<Restaurant> response = restaurantController.createRestaurant(restaurantDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(restaurantId, response.getBody().getId());
        verify(restaurantService).createRestaurant(any(RestaurantDTO.class));
    }

    @Test
    void getRestaurant_WhenExists_ShouldReturnRestaurant() {
        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(restaurant);

        ResponseEntity<Restaurant> response = restaurantController.getRestaurant(restaurantId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(restaurantId, response.getBody().getId());
        verify(restaurantService).getRestaurantById(restaurantId);
    }

    @Test
    void getRestaurant_WhenNotFound_ShouldReturnNotFound() {
        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(null);

        ResponseEntity<Restaurant> response = restaurantController.getRestaurant(restaurantId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(restaurantService).getRestaurantById(restaurantId);
    }

    @Test
    void getAllRestaurants_ShouldReturnListOfRestaurants() {
        List<Restaurant> restaurants = Arrays.asList(restaurant);
        when(restaurantService.getAllRestaurants()).thenReturn(restaurants);

        ResponseEntity<List<Restaurant>> response = restaurantController.getAllRestaurants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        verify(restaurantService).getAllRestaurants();
    }

    @Test
    void getRestaurantsByCuisine_ShouldReturnFilteredList() {
        String cuisineType = "Italian";
        List<Restaurant> restaurants = Arrays.asList(restaurant);
        when(restaurantService.getRestaurantsByCuisineType(cuisineType)).thenReturn(restaurants);

        ResponseEntity<List<Restaurant>> response = restaurantController.getRestaurantsByCuisine(cuisineType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        verify(restaurantService).getRestaurantsByCuisineType(cuisineType);
    }

    @Test
    void getRestaurantsByLocation_WhenFound_ShouldReturnRestaurants() {
        String location = "Test Location";
        List<Restaurant> restaurants = Arrays.asList(restaurant);
        when(restaurantService.getRestaurantsByLocation(location)).thenReturn(restaurants);

        ResponseEntity<List<Restaurant>> response = restaurantController.getRestaurantsByLocation(location);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        verify(restaurantService).getRestaurantsByLocation(location);
    }

    @Test
    void getRestaurantsByLocation_WhenNotFound_ShouldReturnNotFound() {
        String location = "Unknown Location";
        when(restaurantService.getRestaurantsByLocation(location)).thenReturn(Arrays.asList());

        ResponseEntity<List<Restaurant>> response = restaurantController.getRestaurantsByLocation(location);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(restaurantService).getRestaurantsByLocation(location);
    }

    @Test
    void updateRestaurant_WhenExists_ShouldReturnUpdatedRestaurant() {
        when(restaurantService.updateRestaurant(eq(restaurantId), any(RestaurantDTO.class))).thenReturn(restaurant);

        ResponseEntity<Restaurant> response = restaurantController.updateRestaurant(restaurantId, restaurantDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(restaurantId, response.getBody().getId());
        verify(restaurantService).updateRestaurant(eq(restaurantId), any(RestaurantDTO.class));
    }

    @Test
    void updateRestaurant_WhenNotFound_ShouldReturnNotFound() {
        when(restaurantService.updateRestaurant(eq(restaurantId), any(RestaurantDTO.class))).thenReturn(null);

        ResponseEntity<Restaurant> response = restaurantController.updateRestaurant(restaurantId, restaurantDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(restaurantService).updateRestaurant(eq(restaurantId), any(RestaurantDTO.class));
    }

    @Test
    void deleteRestaurant_ShouldReturnNoContent() {
        doNothing().when(restaurantService).deleteRestaurant(restaurantId);

        ResponseEntity<Void> response = restaurantController.deleteRestaurant(restaurantId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(restaurantService).deleteRestaurant(restaurantId);
    }
}