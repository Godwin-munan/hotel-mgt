package com.munan.hotelmgt.service;

import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.RoomType;
import com.munan.hotelmgt.repository.RoomTypeRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    //ADD NEW ROOM TYPE
    public ResponseEntity<HttpResponse<?>> add(RoomType type) throws AlreadyExistException {
        Optional<RoomType> existingtype = roomTypeRepository.findByName(type.getName());

        if(existingtype.isPresent()){
            throw new AlreadyExistException("Room type already exist");
        }

        RoomType newType = new RoomType();
        newType.setName(type.getName());
        newType.setDescription(type.getDescription());
        newType.setPrice(type.getPrice());
        newType.setProperty(type.getProperty());
        newType.setImageUrl(type.getImageUrl());

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        roomTypeRepository.save(newType)
                ));
    }

    //GET ALL ROOM TYPES
    public ResponseEntity<HttpResponse<?>> getAll() {
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        roomTypeRepository.findAll())
        );
    }

    //GET ROOM TYPE BY ID
    public ResponseEntity<HttpResponse<?>> getById(Long id) throws NotFoundException {
        RoomType findType = findById(id);

        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        findType
                ));
    }

    //DELETE ROOM TYPE BY ID
    public ResponseEntity<HttpResponse<?>> deleteById(Long id) throws NotFoundException {
        RoomType findType = findById(id);

        String type = findType.getName();

        roomTypeRepository.delete(findType);

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        "Room Type " + type +" has been deleted"
                ));
    }

    //UPDATE ROOM TYPE
    public ResponseEntity<HttpResponse<?>> update(RoomType type) {
        
        RoomType savedType = new RoomType();
        
        if(type.getId() != null){
            savedType.setId( type.getId());
        }
        
        savedType.setName(type.getName());
        savedType.setDescription(type.getDescription());
        savedType.setPrice(type.getPrice());
        savedType.setProperty(type.getProperty());
        savedType.setImageUrl(type.getImageUrl());
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        roomTypeRepository.save(savedType)
                )); 
    }
    
    //PRIVATE METHOD TO FIND BY ID
    private RoomType findById(Long id) throws NotFoundException {
        return roomTypeRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Room Type with id "+id+" Does not exist"));

    }
    
    //FIND SHIFT BY TYPE
    public RoomType findRoomTypeByName(String name) throws NotFoundException{
        return roomTypeRepository.findByName(name).orElseThrow(()-> new NotFoundException(name+" room not Found"));
    }
    
}
