package com.reservaki.reservaki.application.domain.entity;

import com.reservaki.reservaki.domain.entity.Restaurant;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    void testRestaurantCreationWithAllArgs() {
        UUID testId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(
                testId,
                "Test Restaurant",
                "Test Location",
                "Italian",
                "10:00-22:00",
                50
        );

        assertEquals(testId, restaurant.getId());
        assertEquals("Test Restaurant", restaurant.getName());
        assertEquals("Test Location", restaurant.getLocation());
        assertEquals("Italian", restaurant.getCuisineType());
        assertEquals("10:00-22:00", restaurant.getOpeningHours());
        assertEquals(50, restaurant.getCapacity());
    }

    @Test
    void testRestaurantCreationWithNoArgs() {
        Restaurant restaurant = new Restaurant();

        assertNull(restaurant.getId());
        assertNull(restaurant.getName());
        assertNull(restaurant.getLocation());
        assertNull(restaurant.getCuisineType());
        assertNull(restaurant.getOpeningHours());
        assertNull(restaurant.getCapacity());
    }

    @Test
    void testRestaurantSetters() {
        Restaurant restaurant = new Restaurant();
        UUID testId = UUID.randomUUID();

        restaurant.setId(testId);
        restaurant.setName("Updated Restaurant");
        restaurant.setLocation("Updated Location");
        restaurant.setCuisineType("Japanese");
        restaurant.setOpeningHours("11:00-23:00");
        restaurant.setCapacity(100);

        assertEquals(testId, restaurant.getId());
        assertEquals("Updated Restaurant", restaurant.getName());
        assertEquals("Updated Location", restaurant.getLocation());
        assertEquals("Japanese", restaurant.getCuisineType());
        assertEquals("11:00-23:00", restaurant.getOpeningHours());
        assertEquals(100, restaurant.getCapacity());
    }

    @Test
    void testRestaurantEqualsAndHashCode() {
        UUID id1 = UUID.randomUUID();
        Restaurant restaurant1 = new Restaurant(id1, "Restaurant1", "Location1", "Cuisine1", "10:00-22:00", 50);
        Restaurant restaurant2 = new Restaurant(id1, "Restaurant1", "Location1", "Cuisine1", "10:00-22:00", 50);
        Restaurant restaurant3 = new Restaurant(UUID.randomUUID(), "Different Restaurant", "Different Location", "Different Cuisine", "11:00-23:00", 100);

        // Test equals
        assertEquals(restaurant1, restaurant2);
        assertNotEquals(restaurant1, restaurant3);

        // Test hashCode
        assertEquals(restaurant1.hashCode(), restaurant2.hashCode());
        assertNotEquals(restaurant1.hashCode(), restaurant3.hashCode());
    }

    @Test
    void testRestaurantToString() {
        UUID testId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(
                testId,
                "Test Restaurant",
                "Test Location",
                "Italian",
                "10:00-22:00",
                50
        );

        String expectedToString = restaurant.toString();
        assertNotNull(expectedToString);
        assertTrue(expectedToString.contains("Test Restaurant"));
        assertTrue(expectedToString.contains(testId.toString()));
    }
}