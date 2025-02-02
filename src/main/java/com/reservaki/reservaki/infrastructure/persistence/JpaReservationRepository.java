package com.reservaki.reservaki.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.reservaki.reservaki.domain.entity.Reservation;
import java.time.LocalDateTime;
import java.util.List;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRestaurantId(Long restaurantId);
    List<Reservation> findByRestaurantIdAndReservationDateBetween(
            Long restaurantId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}