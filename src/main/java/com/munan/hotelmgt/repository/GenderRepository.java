package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenderRepository extends JpaRepository<Gender, Long> {
    Optional<Gender> findByType(String type);
}
