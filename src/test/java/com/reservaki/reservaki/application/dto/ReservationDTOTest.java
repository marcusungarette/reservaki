package com.reservaki.reservaki.application.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservationDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    private ReservationDTO createValidReservationDTO() {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(UUID.randomUUID());
        dto.setRestaurantId(UUID.randomUUID());
        dto.setCustomerName("John Doe");
        dto.setCustomerEmail("john@example.com");
        dto.setCustomerPhone("123456789");
        dto.setReservationDate(LocalDateTime.now().plusDays(1));
        dto.setPartySize(2);
        dto.setSpecialRequests("Window seat");
        return dto;
    }

    @Test
    void testValidReservationDTO() {
        ReservationDTO dto = createValidReservationDTO();

        assertTrue(validator.validate(dto).isEmpty());

        assertEquals("John Doe", dto.getCustomerName());
        assertEquals("john@example.com", dto.getCustomerEmail());
        assertEquals("123456789", dto.getCustomerPhone());
        assertNotNull(dto.getReservationDate());
        assertEquals(2, dto.getPartySize());
        assertEquals("Window seat", dto.getSpecialRequests());
        assertNotNull(dto.getId());
        assertNotNull(dto.getRestaurantId());
    }

    @Test
    void testReservationDTORestaurantIdValidation() {
        ReservationDTO dto = createValidReservationDTO();
        dto.setRestaurantId(null);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void testReservationDTOCustomerNameValidation() {
        ReservationDTO dto = createValidReservationDTO();
        dto.setCustomerName(null);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void testReservationDTOEmailValidation() {
        ReservationDTO dto = createValidReservationDTO();
        dto.setCustomerEmail("invalid-email");

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void testReservationDTOReservationDateValidation() {
        ReservationDTO dto = createValidReservationDTO();
        dto.setReservationDate(LocalDateTime.now().minusDays(1));

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void testReservationDTOPartySizeValidation() {
        ReservationDTO dto = createValidReservationDTO();
        dto.setPartySize(0);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void testReservationDTOSettersAndGetters() {
        ReservationDTO dto = new ReservationDTO();
        UUID testId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        LocalDateTime reservationDate = LocalDateTime.now().plusDays(2);

        dto.setId(testId);
        dto.setRestaurantId(restaurantId);
        dto.setCustomerName("Jane Doe");
        dto.setCustomerEmail("jane@example.com");
        dto.setCustomerPhone("987654321");
        dto.setReservationDate(reservationDate);
        dto.setPartySize(4);
        dto.setSpecialRequests("Vegan menu");

        assertEquals(testId, dto.getId());
        assertEquals(restaurantId, dto.getRestaurantId());
        assertEquals("Jane Doe", dto.getCustomerName());
        assertEquals("jane@example.com", dto.getCustomerEmail());
        assertEquals("987654321", dto.getCustomerPhone());
        assertEquals(reservationDate, dto.getReservationDate());
        assertEquals(4, dto.getPartySize());
        assertEquals("Vegan menu", dto.getSpecialRequests());
    }

    @Test
    void testReservationDTOToString() {
        ReservationDTO dto = createValidReservationDTO();

        String toString = dto.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("John Doe"));
        assertTrue(toString.contains(dto.getId().toString()));
    }

    @Test
    void testReservationDTOEqualsAndHashCode() {
        ReservationDTO dto1 = createValidReservationDTO();
        ReservationDTO dto2 = new ReservationDTO();
        dto2.setId(dto1.getId());
        dto2.setRestaurantId(dto1.getRestaurantId());
        dto2.setCustomerName(dto1.getCustomerName());
        dto2.setCustomerEmail(dto1.getCustomerEmail());
        dto2.setCustomerPhone(dto1.getCustomerPhone());
        dto2.setReservationDate(dto1.getReservationDate());
        dto2.setPartySize(dto1.getPartySize());
        dto2.setSpecialRequests(dto1.getSpecialRequests());

        ReservationDTO dto3 = createValidReservationDTO();
        dto3.setId(UUID.randomUUID());

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}