package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
