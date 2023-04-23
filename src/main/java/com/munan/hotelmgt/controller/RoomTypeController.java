package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.RoomType;
import com.munan.hotelmgt.service.RoomTypeService;
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
@RequestMapping("/api/roomtype")
@Tag(name = "Room Type Controller", description = "Room Type Controller")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;
    
    //ADD
    @Operation(summary = "ADD ROOM TYPE", description = "Add a new Room type")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<?>> addRoomType(@RequestBody RoomType type) throws AlreadyExistException, Exception {
        return roomTypeService.add(type);
    }

    
    //GET
    @Operation(summary = "GET ALL", description = "Retrieve all existing Room Type")
    @GetMapping("/get")
    public ResponseEntity<HttpResponse<?>> getAllRoomType() throws Exception { return roomTypeService.getAll();}

    @Operation(summary = "RETRIEVE BY ID", description = "Retrieve Room Type by id")
    @GetMapping("/get/{roomType_id}")
    public ResponseEntity<HttpResponse<?>> getByRoomTypeId(@PathVariable(value = "roomType_id") Long id) throws NotFoundException {
        return roomTypeService.getById(id);
    }
    
    @Operation(summary = "GET TOTAL ROOM TYPE COUNT", description = "Retrieve total room type count")
    @GetMapping("/get/total")
    public ResponseEntity<HttpResponse<?>> getTotalRoomTypeCount() throws NotFoundException {
        return roomTypeService.totalRoomTypeCount();
    }

    //DELETE
    @Operation(summary = "DELETE BY ID", description = "Delete existing Room Type by id")
    @DeleteMapping("/delete/{roomType_id}")
    public ResponseEntity<HttpResponse<?>> deleteRoomTypeById(@PathVariable("roomType_id") Long id) throws NotFoundException, AlreadyExistException {
        return roomTypeService.deleteById(id);
    }

    //UPDATE
    @Operation(summary = "UPDATE ROOM TYPE", description = "Update existing Room Type by id")
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<?>> updateRoomType(@RequestBody RoomType type) throws Exception{
        return roomTypeService.update(type);
    }
}
