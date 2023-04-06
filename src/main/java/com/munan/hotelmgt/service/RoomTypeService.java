package com.munan.hotelmgt.service;

import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.RoomType;
import com.munan.hotelmgt.repository.RoomTypeRepository;
import com.munan.hotelmgt.utils.CompressionUtil;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
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
    public ResponseEntity<HttpResponse<?>> add(RoomType type) throws AlreadyExistException, IOException {
        Optional<RoomType> existingtype = roomTypeRepository.findByName(type.getName());

        if(existingtype.isPresent()){
            throw new AlreadyExistException("Room type already exist");
        }

        RoomType newType = new RoomType();
        newType.setName(type.getName());
        newType.setDescription(type.getDescription());
        newType.setPrice(type.getPrice());
        newType.setProperty(type.getProperty());
        
        if(type.getImage() != null && type.getImage().length > 0){
            newType.setImage(
                CompressionUtil.compress(type.getImage())
                );
        }
        

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        roomTypeRepository.save(newType)
                ));
    }

    //GET ALL ROOM TYPES
    public ResponseEntity<HttpResponse<?>> getAll() throws IOException, DataFormatException {
        List<RoomType> roomList = roomTypeRepository.findAll();
        
//        var rooms = decompressed(roomList);
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        roomList)
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
    public ResponseEntity<HttpResponse<?>> update(RoomType type) throws IOException {
        
        RoomType savedType = new RoomType();
        
        if(type.getId() != null){
            savedType.setId( type.getId());
        }
        
        savedType.setName(type.getName());
        savedType.setDescription(type.getDescription());
        savedType.setPrice(type.getPrice());
        savedType.setProperty(type.getProperty());
        savedType.setImage(
                CompressionUtil.compress(type.getImage())
        );
        
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
    
    //DECOMPRESS IMAGE BYTE
    private List<RoomType> decompressed(List<RoomType> rooms) throws IOException, DataFormatException{
        
        List<RoomType> newTypes = null;
        
        Iterator<RoomType> roomIt = rooms.iterator();
        
        while(roomIt.hasNext()){
        var image = CompressionUtil.decompress(roomIt.next().getImage());
//        RoomType roomt = roomIt.next().setImage(image);
//          newTypes.add();
        }
        
//        rooms.stream().map(rooms -> {
//        
//            List<RoomType> newTypes = 
//        });
//       
        return rooms;
    }
    
    
    //FIND SHIFT BY TYPE
    public RoomType findRoomTypeByName(String name) throws NotFoundException{
        return roomTypeRepository.findByName(name).orElseThrow(()-> new NotFoundException(name+" room not Found"));
    }
    
}
