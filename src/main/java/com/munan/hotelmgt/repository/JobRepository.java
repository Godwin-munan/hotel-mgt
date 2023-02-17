package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    Optional<Job> findByTitle(String title);
}
