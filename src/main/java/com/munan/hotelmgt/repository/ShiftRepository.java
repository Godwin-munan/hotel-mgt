package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
