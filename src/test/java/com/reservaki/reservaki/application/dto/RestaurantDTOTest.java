package com.reservaki.reservaki.application.dto;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    private RestaurantDTO createValidRestaurantDTO() {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setId(UUID.randomUUID());
        dto.setName("Test Restaurant");
        dto.setLocation("Test Location");
        dto.setCuisineType("Italian");
        dto.setOpeningHours("10:00-22:00");
        dto.setCapacity(50);
        return dto;
    }

    @Test
    void testValidRestaurantDTO() {
        RestaurantDTO dto = createValidRestaurantDTO();

        assertTrue(validator.validate(dto).isEmpty());

        assertEquals("Test Restaurant", dto.getName());
        assertEquals("Test Location", dto.getLocation());
        assertEquals("Italian", dto.getCuisineType());
        assertEquals("10:00-22:00", dto.getOpeningHours());
        assertEquals(50, dto.getCapacity());
        assertNotNull(dto.getId());
    }

    @Test
    void testRestaurantDTONameValidation() {
        RestaurantDTO dto = createValidRestaurantDTO();
        dto.setName("");

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void testRestaurantDTOLocationValidation() {
        RestaurantDTO dto = createValidRestaurantDTO();
        dto.setLocation("");

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void testRestaurantDTOCapacityValidation() {
        RestaurantDTO dto = createValidRestaurantDTO();
        dto.setCapacity(0);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void testRestaurantDTOSettersAndGetters() {
        RestaurantDTO dto = new RestaurantDTO();
        UUID testId = UUID.randomUUID();

        dto.setId(testId);
        dto.setName("New Restaurant");
        dto.setLocation("New Location");
        dto.setCuisineType("Japanese");
        dto.setOpeningHours("11:00-23:00");
        dto.setCapacity(30);

        assertEquals(testId, dto.getId());
        assertEquals("New Restaurant", dto.getName());
        assertEquals("New Location", dto.getLocation());
        assertEquals("Japanese", dto.getCuisineType());
        assertEquals("11:00-23:00", dto.getOpeningHours());
        assertEquals(30, dto.getCapacity());
    }

    @Test
    void testRestaurantDTOToString() {
        RestaurantDTO dto = createValidRestaurantDTO();

        String toString = dto.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Test Restaurant"));
        assertTrue(toString.contains(dto.getId().toString()));
    }

    @Test
    void testRestaurantDTOEqualsAndHashCode() {
        RestaurantDTO dto1 = createValidRestaurantDTO();
        RestaurantDTO dto2 = createValidRestaurantDTO();
        dto2.setId(dto1.getId());

        RestaurantDTO dto3 = createValidRestaurantDTO();
        dto3.setId(UUID.randomUUID());

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}