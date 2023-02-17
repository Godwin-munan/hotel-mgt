package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
