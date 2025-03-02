package com.reservaki.reservaki.application.domain.entity;


import com.reservaki.reservaki.domain.entity.Reservation;
import com.reservaki.reservaki.domain.entity.ReservationStatus;
import com.reservaki.reservaki.domain.entity.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private Restaurant restaurant;
    private UUID reservationId;
    private LocalDateTime reservationDate;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());
        restaurant.setName("Test Restaurant");

        reservationId = UUID.randomUUID();
        reservationDate = LocalDateTime.now().plusDays(1);
    }

    @Test
    void testReservationCreationWithAllArgs() {
        Reservation reservation = new Reservation(
                reservationId,
                restaurant,
                "John Doe",
                "john@example.com",
                "123456789",
                reservationDate,
                4,
                ReservationStatus.CONFIRMED,
                "Vegetarian table"
        );

        assertEquals(reservationId, reservation.getId());
        assertEquals(restaurant, reservation.getRestaurant());
        assertEquals("John Doe", reservation.getCustomerName());
        assertEquals("john@example.com", reservation.getCustomerEmail());
        assertEquals("123456789", reservation.getCustomerPhone());
        assertEquals(reservationDate, reservation.getReservationDate());
        assertEquals(4, reservation.getPartySize());
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
        assertEquals("Vegetarian table", reservation.getSpecialRequests());
    }

    @Test
    void testReservationCreationWithNoArgs() {
        Reservation reservation = new Reservation();

        assertNull(reservation.getId());
        assertNull(reservation.getRestaurant());
        assertNull(reservation.getCustomerName());
        assertNull(reservation.getCustomerEmail());
        assertNull(reservation.getCustomerPhone());
        assertNull(reservation.getReservationDate());
        assertNull(reservation.getPartySize());
        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
        assertNull(reservation.getSpecialRequests());
    }

    @Test
    void testReservationSetters() {
        Reservation reservation = createReservationWithSetters();

        verifyReservationSetters(reservation);
    }

    private Reservation createReservationWithSetters() {
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setRestaurant(restaurant);
        reservation.setCustomerName("Jane Doe");
        reservation.setCustomerEmail("jane@example.com");
        reservation.setCustomerPhone("987654321");
        reservation.setReservationDate(reservationDate);
        reservation.setPartySize(2);
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setSpecialRequests("Window seat");

        return reservation;
    }

    private void verifyReservationSetters(Reservation reservation) {
        assertEquals(reservationId, reservation.getId());
        assertEquals(restaurant, reservation.getRestaurant());
        assertEquals("Jane Doe", reservation.getCustomerName());
        assertEquals("jane@example.com", reservation.getCustomerEmail());
        assertEquals("987654321", reservation.getCustomerPhone());
        assertEquals(reservationDate, reservation.getReservationDate());
        assertEquals(2, reservation.getPartySize());
        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
        assertEquals("Window seat", reservation.getSpecialRequests());
    }

    @Test
    void testReservationDefaultStatus() {
        Reservation reservation = new Reservation();
        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
    }

    @Test
    void testReservationEqualsAndHashCode() {
        Reservation reservation1 = new Reservation(
                reservationId,
                restaurant,
                "John Doe",
                "john@example.com",
                "123456789",
                reservationDate,
                4,
                ReservationStatus.CONFIRMED,
                "Vegetarian table"
        );

        Reservation reservation2 = new Reservation(
                reservationId,
                restaurant,
                "John Doe",
                "john@example.com",
                "123456789",
                reservationDate,
                4,
                ReservationStatus.CONFIRMED,
                "Vegetarian table"
        );

        Reservation reservation3 = new Reservation(
                UUID.randomUUID(),
                new Restaurant(),
                "Jane Doe",
                "jane@example.com",
                "987654321",
                LocalDateTime.now().plusDays(2),
                2,
                ReservationStatus.PENDING,
                null
        );

        // Test equals
        assertEquals(reservation1, reservation2);
        assertNotEquals(reservation1, reservation3);

        // Test hashCode
        assertEquals(reservation1.hashCode(), reservation2.hashCode());
        assertNotEquals(reservation1.hashCode(), reservation3.hashCode());
    }

    @Test
    void testReservationToString() {
        Reservation reservation = new Reservation(
                reservationId,
                restaurant,
                "John Doe",
                "john@example.com",
                "123456789",
                reservationDate,
                4,
                ReservationStatus.CONFIRMED,
                "Vegetarian table"
        );

        String expectedToString = reservation.toString();
        assertNotNull(expectedToString);
        assertTrue(expectedToString.contains("John Doe"));
        assertTrue(expectedToString.contains(reservationId.toString()));
    }
}