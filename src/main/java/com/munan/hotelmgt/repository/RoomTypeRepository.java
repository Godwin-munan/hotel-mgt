package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.RoomType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    
    @Query(
            value ="""
                   SELECT COUNT(*)
                   \t FROM `room_type`""" ,nativeQuery = true
    )
    Integer findRoomTypeCount();
    
    Optional<RoomType> findByName(String name);
    
}
