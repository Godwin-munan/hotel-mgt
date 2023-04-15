package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.dto.RoomDto;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Room;
import com.munan.hotelmgt.service.RoomService;
import com.munan.hotelmgt.utils.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room")
@Tag(name = "Room Controller", description = "Room Controller")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    
    //ADD
    @Operation(summary = "ADD ROOM", description = "Add a new Room")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<?>> addRoom(@RequestBody RoomDto roomDto) throws AlreadyExistException, NotFoundException {
        return roomService.addRoom(roomDto);
    }

    //GET
    @Operation(summary = "GET ALL", description = "Retrieve all Room")
    @GetMapping("/get/{field}/{page}/{size}")
    public ResponseEntity<HttpResponse<?>> getAllRooms(@PathVariable("field") String field,
                                                      @PathVariable("page") Integer page,
                                                      @PathVariable("size") Integer size) throws NotFoundException {
        return roomService.getAll(page, size, field);
    }
    
    @Operation(summary = "GET ROOMS BY ROOM TYPE ID", description = "Retrieve Rooms by room type id")
    @GetMapping("/get/type/{roomtype_id}")
    public ResponseEntity<HttpResponse<?>> getRoomsByRoomTypeId(@PathVariable("roomtype_id") Long typeId) throws NotFoundException {
        return roomService.getRoomsByTypeId(typeId);
    }
    
    @Operation(summary = "GET BY ID", description = "Retrieve existing Room by id")
    @GetMapping("/get/{room_id}")
    public ResponseEntity<HttpResponse<?>> getRoomById(@PathVariable(value = "room_id") Long id) throws NotFoundException {
        return roomService.getById(id);
    }
    
    @Operation(summary = "GET AVAILABLE ROOM COUNT", description = "Retrieve number of available rooms")
    @GetMapping("/get/availableCount")
    public ResponseEntity<HttpResponse<?>> getAvailableRoomCount() throws NotFoundException {
        return roomService.availableRoomCount();
    }
    
    @Operation(summary = "GET OCCUPIED ROOM COUNT", description = "Retrieve number of occupied rooms")
    @GetMapping("/get/occupiedCount")
    public ResponseEntity<HttpResponse<?>> getOccupiedRoomCount() throws NotFoundException {
        return roomService.occupiedRoomCount();
    }

    
    //DELETE
    @Operation(summary = "DELETE BY ID", description = "Remove Room by id")
    @DeleteMapping("/delete/{room_id}")
    public ResponseEntity<HttpResponse<?>> deleteRoomById(@PathVariable(value = "room_id") Long id) throws NotFoundException {
        return roomService.deleteById(id);
    }
    
    
    //UPDATE
    @Operation(summary = "UPDATE CARD", description = "Update room record")
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<?>> updateRoom(@RequestBody Room room) throws NotFoundException{
        return roomService.update(room);
    }
}
