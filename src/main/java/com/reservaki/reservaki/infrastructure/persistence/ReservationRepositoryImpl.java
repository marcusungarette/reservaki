package com.reservaki.reservaki.infrastructure.persistence;


import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.reservaki.reservaki.domain.repository.ReservationRepository;
import com.reservaki.reservaki.domain.entity.Reservation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JpaReservationRepository jpaRepository;

    @Override
    public Reservation save(Reservation reservation) {
        return jpaRepository.save(reservation);
    }

    @Override
    public Reservation findById(UUID id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reservation> findByRestaurantId(UUID restaurantId) {
        return jpaRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<Reservation> findByRestaurantIdAndDate(UUID restaurantId, LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return jpaRepository.findByRestaurantIdAndReservationDateBetween(
                restaurantId, startOfDay, endOfDay);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}