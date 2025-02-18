package com.reservaki.reservaki.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.reservaki.reservaki.domain.entity.Reservation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface JpaReservationRepository extends JpaRepository<Reservation, UUID> {  // mudado de Long para UUID
    List<Reservation> findByRestaurantId(UUID restaurantId);
    List<Reservation> findByRestaurantIdAndReservationDateBetween(
            UUID restaurantId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}