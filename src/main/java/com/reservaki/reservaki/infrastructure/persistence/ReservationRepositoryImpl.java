package com.reservaki.reservaki.infrastructure.persistence;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.reservaki.reservaki.domain.repository.ReservationRepository;
import com.reservaki.reservaki.domain.entity.Reservation;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JpaReservationRepository jpaRepository;

    @Override
    public Reservation save(Reservation reservation) {
        return jpaRepository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reservation> findByRestaurantId(Long restaurantId) {
        return jpaRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<Reservation> findByRestaurantIdAndDate(Long restaurantId, LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return jpaRepository.findByRestaurantIdAndReservationDateBetween(
                restaurantId, startOfDay, endOfDay);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}