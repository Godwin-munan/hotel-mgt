package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
