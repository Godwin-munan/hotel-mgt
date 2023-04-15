package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, Long> {
    
    
    @Query(
            value ="""
                   SELECT COUNT(*)
                   \t FROM room  
                   \t WHERE `room_status` =?1 AND `deleted` = FALSE""" ,nativeQuery = true
    )
    Integer  findStatusCount(String name);
    
    Optional<Room> findByCode(String code);
    
    Optional<Room> findByStatus(String status);
    
    List<Room> findByRoomType_id(Long roomtype_id);
}
