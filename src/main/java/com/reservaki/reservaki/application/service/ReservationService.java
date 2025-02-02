package com.reservaki.reservaki.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.reservaki.reservaki.domain.entity.Reservation;
import com.reservaki.reservaki.domain.entity.Restaurant;
import com.reservaki.reservaki.domain.entity.ReservationStatus;
import com.reservaki.reservaki.domain.repository.ReservationRepository;
import com.reservaki.reservaki.domain.repository.RestaurantRepository;
import com.reservaki.reservaki.application.dto.ReservationDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Reservation createReservation(ReservationDTO dto) {
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId());
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant not found");
        }

        // Verificar disponibilidade
        List<Reservation> existingReservations =
                reservationRepository.findByRestaurantIdAndDate(
                        dto.getRestaurantId(),
                        dto.getReservationDate()
                );

        int totalReservedSeats = existingReservations.stream()
                .mapToInt(Reservation::getPartySize)
                .sum();

        if (totalReservedSeats + dto.getPartySize() > restaurant.getCapacity()) {
            throw new IllegalStateException("Restaurant capacity exceeded for this time");
        }

        Reservation reservation = new Reservation();
        reservation.setRestaurant(restaurant);
        reservation.setCustomerName(dto.getCustomerName());
        reservation.setCustomerEmail(dto.getCustomerEmail());
        reservation.setCustomerPhone(dto.getCustomerPhone());
        reservation.setReservationDate(dto.getReservationDate());
        reservation.setPartySize(dto.getPartySize());
        reservation.setSpecialRequests(dto.getSpecialRequests());
        reservation.setStatus(ReservationStatus.PENDING);

        return reservationRepository.save(reservation);
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getRestaurantReservations(Long restaurantId) {
        return reservationRepository.findByRestaurantId(restaurantId);
    }

    @Transactional
    public Reservation updateReservationStatus(Long id, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(id);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }

        reservation.setStatus(status);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }
}