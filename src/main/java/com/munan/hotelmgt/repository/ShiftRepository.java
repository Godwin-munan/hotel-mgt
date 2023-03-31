package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.Shift;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

    public Optional<Shift> findByType(String type);
}
