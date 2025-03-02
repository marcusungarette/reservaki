package com.reservaki.reservaki.application.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReviewDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    private ReviewDTO createValidReviewDTO() {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(UUID.randomUUID());
        dto.setRestaurantId(UUID.randomUUID());
        dto.setCustomerName("John Doe");
        dto.setCustomerEmail("john@example.com");
        dto.setRating(4);
        dto.setComment("Great food and excellent service!");
        return dto;
    }

    @Test
    void testValidReviewDTO() {
        ReviewDTO dto = createValidReviewDTO();

        assertTrue(validator.validate(dto).isEmpty());

        assertEquals("John Doe", dto.getCustomerName());
        assertEquals("john@example.com", dto.getCustomerEmail());
        assertEquals(4, dto.getRating());
        assertEquals("Great food and excellent service!", dto.getComment());
        assertNotNull(dto.getId());
        assertNotNull(dto.getRestaurantId());
    }

    @Test
    void testReviewDTORestaurantIdValidation() {
        ReviewDTO dto = createValidReviewDTO();
        dto.setRestaurantId(null);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void testReviewDTOCustomerNameValidation() {
        ReviewDTO dto = createValidReviewDTO();
        dto.setCustomerName("");

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void testReviewDTOEmailValidation() {
        ReviewDTO dto = createValidReviewDTO();
        dto.setCustomerEmail("invalid-email");

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void testReviewDTORatingValidation() {
        ReviewDTO dto = createValidReviewDTO();
        dto.setRating(0);

        assertFalse(validator.validate(dto).isEmpty());

        dto.setRating(6);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void testReviewDTOCommentValidation() {
        ReviewDTO dto = createValidReviewDTO();
        dto.setComment(String.join("", Collections.nCopies(1001, "a")));

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void testReviewDTOSettersAndGetters() {
        ReviewDTO dto = new ReviewDTO();
        UUID testId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        dto.setId(testId);
        dto.setRestaurantId(restaurantId);
        dto.setCustomerName("Jane Doe");
        dto.setCustomerEmail("jane@example.com");
        dto.setRating(5);
        dto.setComment("Excellent experience!");

        assertEquals(testId, dto.getId());
        assertEquals(restaurantId, dto.getRestaurantId());
        assertEquals("Jane Doe", dto.getCustomerName());
        assertEquals("jane@example.com", dto.getCustomerEmail());
        assertEquals(5, dto.getRating());
        assertEquals("Excellent experience!", dto.getComment());
    }

    @Test
    void testReviewDTOToString() {
        ReviewDTO dto = createValidReviewDTO();

        String toString = dto.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("John Doe"));
        assertTrue(toString.contains(dto.getId().toString()));
    }

    @Test
    void testReviewDTOEqualsAndHashCode() {
        ReviewDTO dto1 = createValidReviewDTO();
        ReviewDTO dto2 = new ReviewDTO();
        dto2.setId(dto1.getId());
        dto2.setRestaurantId(dto1.getRestaurantId());
        dto2.setCustomerName(dto1.getCustomerName());
        dto2.setCustomerEmail(dto1.getCustomerEmail());
        dto2.setRating(dto1.getRating());
        dto2.setComment(dto1.getComment());

        ReviewDTO dto3 = createValidReviewDTO();
        dto3.setId(UUID.randomUUID());

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}