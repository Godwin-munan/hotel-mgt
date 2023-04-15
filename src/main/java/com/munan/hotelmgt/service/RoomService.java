package com.munan.hotelmgt.service;

import static com.munan.hotelmgt.constant.GenConstant.ROOM_AVAILABLE;
import static com.munan.hotelmgt.constant.GenConstant.ROOM_OCCUPIED;
import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.dto.RoomDto;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Room;
import com.munan.hotelmgt.model.RoomType;
import com.munan.hotelmgt.repository.RoomRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeService roomTypeService;

    //FIND ROOM BY NUMBER
    public Room findRoomByNo(String code) throws NotFoundException {
        return roomRepository.findByCode(code).orElseThrow(()-> new NotFoundException("Room number "+code+" not Found"));
    }
    

    //ADD NEW ROOM
    public ResponseEntity<HttpResponse<?>> addRoom(RoomDto roomDto) throws NotFoundException, AlreadyExistException {
        
        Optional<Room> findRoom = roomRepository.findByCode(roomDto.getCode());
        if(findRoom.isPresent()){
            throw new AlreadyExistException("Room with code "+roomDto.getCode()+" already exists");
        }
        
        Room newRoom = addRoomFromDto(roomDto);
   
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        roomRepository.save(newRoom)
                )
        );
    }

    //GET ALL ROOMS
    public ResponseEntity<HttpResponse<?>> getAll(Integer page, Integer size, String field) {
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        roomRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.ASC, field))))
        );
    }
    
    //GET ROOMS BY ROOM TYPE ID
    public ResponseEntity<HttpResponse<?>> getRoomsByTypeId(Long typeId) throws NotFoundException {
        
        List<Room> rooms = findByRoomTypeId(typeId);
        
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        rooms)
        );
    }

    //GET ROOM BY ID
    public ResponseEntity<HttpResponse<?>> getById(Long id) throws NotFoundException {
        Room findRoom = findById(id);
        

        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        findRoom)
        );
    }
    
    //GET COUNT OF AVAILABLE ROOMS
    public ResponseEntity<HttpResponse<?>> availableRoomCount() {
       return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        roomRepository.findStatusCount(ROOM_AVAILABLE))
        ); 
    }
    
    //GET COUNT OF OCCUPIED ROOMS
    public ResponseEntity<HttpResponse<?>> occupiedRoomCount() {
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        roomRepository.findStatusCount(ROOM_OCCUPIED))
        ); 
    }

    //DELETE ROOM BY ID
    public ResponseEntity<HttpResponse<?>> deleteById(Long id) throws NotFoundException {
        Room findRoom = findById(id);

        String code = findRoom.getCode();
        
        findRoom = removeGuestFromRoom(findRoom);
        
        findRoom.setDeleted(true);

        roomRepository.delete(findRoom);

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        "Room number " + code +" has been deleted")
        );
    }
    

    //UPDATE ROOM
    public ResponseEntity<HttpResponse<?>> update(Room room) throws NotFoundException {
        Room savedRoom = addRoomFromRoom(room);
        
        if(room.getId() != null){
            savedRoom.setId(room.getId());
        }
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        roomRepository.save(savedRoom))
        );
    }
    
    //METHOD TO FIND BY ID
    public Room findById(Long id) throws NotFoundException {

        return roomRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Room with id "+id+", Does not exist"));

    }
    
    //FIND ROOMS BY ROOM TYPE ID
    public List<Room> findByRoomTypeId(Long id) throws NotFoundException {
        
        roomTypeService.findById(id);
        return roomRepository.findByRoomType_id(id);
    }
    
    
    //PRIVATE METHOD TO ADD NEW ROOM FROM DTO
    private Room addRoomFromDto(RoomDto room) throws NotFoundException{
    
        Room newRoom = new Room();
        
        RoomType roomType = roomTypeService.findById(room.getRoomTypeId());
        
        newRoom.setCode(room.getCode());
        newRoom.setStatus(ROOM_AVAILABLE);
        newRoom.setRoomType(roomType);
        
        return newRoom;
    }
    
    //PRIVATE METHOD TO ADD NEW ROOM FROM ROOM
    private Room addRoomFromRoom(Room room) throws NotFoundException{
    
        Room newRoom = new Room();
        
        newRoom.setCode(room.getCode());
        newRoom.setStatus(room.getStatus());
        newRoom.setRoomType(room.getRoomType());
        
        return newRoom;
    }

    //PRIVATE METHOD TO REMOVE ROOM FROM GUEST
    private Room removeGuestFromRoom(Room room){
        
        room.getGuests().clear();
        
        return roomRepository.save(room);
    }

    

    
  
}
