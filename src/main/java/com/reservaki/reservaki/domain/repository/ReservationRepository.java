package com.reservaki.reservaki.domain.repository;

import com.reservaki.reservaki.domain.entity.Reservation;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Reservation findById(Long id);
    List<Reservation> findByRestaurantId(Long restaurantId);
    List<Reservation> findByRestaurantIdAndDate(Long restaurantId, LocalDateTime date);
    void deleteById(Long id);
}