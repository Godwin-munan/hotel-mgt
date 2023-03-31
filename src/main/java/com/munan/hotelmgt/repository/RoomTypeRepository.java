package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.RoomType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    
    Optional<RoomType> findByName(String name);
}
