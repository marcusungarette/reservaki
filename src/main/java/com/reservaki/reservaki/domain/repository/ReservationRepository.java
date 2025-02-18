package com.reservaki.reservaki.domain.repository;

import com.reservaki.reservaki.domain.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Reservation findById(UUID id);
    List<Reservation> findByRestaurantId(UUID restaurantId);
    List<Reservation> findByRestaurantIdAndDate(UUID restaurantId, LocalDateTime date);
    void deleteById(UUID id);
}