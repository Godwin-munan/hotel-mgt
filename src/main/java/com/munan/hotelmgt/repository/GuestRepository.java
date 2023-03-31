package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.Guest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    
    @Query(
            value ="""
                   SELECT COUNT(*)
                   \t FROM guest 
                   \t WHERE `check_out` IS NULL AND `deleted` = FALSE""" ,nativeQuery = true
    )
    Integer  findCurrentCount();
    

    Optional<List<Guest>> findByCheckOut(LocalDate date);
    
    Optional<Guest> findByEmail(String email);
    
    Optional<Guest> findByGuestCode(String guestCode);
    

}
