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
    
    
    @Query(
            value ="""
                   SELECT COUNT(*)
                   \t FROM room  
                   \t WHERE `deleted` = FALSE""" ,nativeQuery = true
    )
    Integer findRoomCount();
    
    @Query(
            value ="""
                   SELECT *
                   \t FROM room  
                   \t WHERE `roomType_id` =?1 AND `room_status` = ?2 AND `deleted` = FALSE""" ,nativeQuery = true
    )
    List<Room> findAvailableRoomByRoomTypeId(Long id, String status);
    
    Optional<Room> findByCode(String code);
    
    Optional<Room> findByStatus(String status);
    
    List<Room> findByRoomType_id(Long roomtypeId);
}
