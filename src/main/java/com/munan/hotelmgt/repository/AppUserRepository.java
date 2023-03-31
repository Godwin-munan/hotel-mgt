package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.AppUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {


    Optional<AppUser> findByUsername(String email);
    
    List<AppUser> findByRoles_id(Long id);
}
