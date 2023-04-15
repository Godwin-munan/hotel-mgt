package com.munan.hotelmgt.service;

import com.munan.hotelmgt.constant.GenConstant;
import static com.munan.hotelmgt.constant.GenConstant.GUEST_INITIAL;
import static com.munan.hotelmgt.constant.GenConstant.ROOM_AVAILABLE;
import static com.munan.hotelmgt.constant.GenConstant.ROOM_OCCUPIED;
import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.dto.CheckoutDto;
import com.munan.hotelmgt.dto.GuestDto;
import com.munan.hotelmgt.dto.RoomDto;
import com.munan.hotelmgt.dto.RoomListDto;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.InvalidEmailException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Gender;
import com.munan.hotelmgt.model.Guest;
import com.munan.hotelmgt.model.Room;
import com.munan.hotelmgt.repository.GuestRepository;
import com.munan.hotelmgt.repository.RoomRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;
    private final GenderService genderService;
    private final RoomService roomService;

    
    //ADD NEW GUEST
    public ResponseEntity<HttpResponse<?>> add(GuestDto guestDto) throws AlreadyExistException, NotFoundException, NoSuchAlgorithmException, InvalidEmailException {
      
        Guest newGuest = addGuestFromDto(guestDto);
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        guestRepository.save(newGuest)
                )
        );
    }
    
    //ADD ROOM LIST TO GUEST
    public ResponseEntity<HttpResponse<?>> addRoomList(Long gId, RoomListDto roomList) throws NotFoundException {
        Guest guest = findById(gId);
        Guest updateGuest = addRoomToGuest(guest, roomList);
 
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        guestRepository.save(updateGuest))
        );
    }

    //GET ALL GUESTS
    public ResponseEntity<HttpResponse<?>> getAll(Integer page, Integer size, String field) {
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        guestRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.ASC, field))))
        );
    }

    //GET GUEST BY ID
    public ResponseEntity<HttpResponse<?>> getById(Long id) throws NotFoundException {
        Guest findGuest = findById(id);
        

        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        findGuest)
        );
    }
    
    //GET CURRENT GUEST COUNT
    public ResponseEntity<HttpResponse<?>> currentGuestCount() {
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        guestRepository.findCurrentCount())
        ); 
    }
    
    //GET ALL CURRENT GUESTS
    public ResponseEntity<HttpResponse<?>> currentGuest() {
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        guestRepository.findByCheckOut(null))
        ); 
    }

    //DELETE STAFF BY ID
    public ResponseEntity<HttpResponse<?>> deleteById(Long id) throws NotFoundException {
        Guest findGuest = findById(id);
        
        findGuest = removeRoomFromGuest(findGuest);
        
        findGuest.setDeleted(true);

        String name = findGuest.getFirstName();

        guestRepository.delete(findGuest);

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        "Guest with name " + name +" has been deleted")
        );
    }

    //UPDATE GUEST
    public ResponseEntity<HttpResponse<?>> update(Guest guest) throws NotFoundException, InvalidEmailException {
        
        Guest savedGuest = addGuestFromGuest(guest);
        
        if(guest.getId() != null){
            savedGuest.setId(guest.getId());
        }
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        guestRepository.save(savedGuest))
        );
    }
    
    //CHECK GUEST OUT
    public ResponseEntity<HttpResponse<?>> checkOut(CheckoutDto checkDto) throws NotFoundException {
        Guest guest = findById(checkDto.getGuest_id());
        
        guest = removeRoomFromGuest(guest);
        
        guest.setCheckOut(checkDto.getCheckOut());
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        guestRepository.save(guest))
        );
    }
    
    //PUBLIC METHOD TO FIND BY ID
    public Guest findById(Long id) throws NotFoundException {

        return guestRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Guest with id "+id+" Does not exist"));

    }
    
    //PRIVATE METHOD TO ADD NEW GUEST FROM DTO
    private Guest addGuestFromDto(GuestDto guest) throws NotFoundException, AlreadyExistException, NoSuchAlgorithmException, InvalidEmailException{
    
        if(!GenConstant.emailValidator(guest.getEmail())){
            throw new InvalidEmailException("Invalid email");
        }
        
        String guestCode = randomNum();
        
        Guest newGuest = new Guest();
        Gender gender = genderService.findGenderById(guest.getGenderId());
        
        newGuest.setFirstName(guest.getFirstName());
        newGuest.setLastName(guest.getLastName());
        newGuest.setGuestCode(guestCode);
        newGuest.setCheckIn(guest.getCheckIn());
        newGuest.setCheckOut(null);
        newGuest.setExpireDate(guest.getExpireDate());
        newGuest.setEmail(guest.getEmail());
        newGuest.setGender(gender);
        
        return newGuest;
    }
    
    //PRIVATE METHOD TO ADD NEW GUEST FROM GUEST
    private Guest addGuestFromGuest(Guest guest) throws NotFoundException, InvalidEmailException{
    
        Guest newGuest = findById(guest.getId());
        
        if(!GenConstant.emailValidator(guest.getEmail())){
            throw new InvalidEmailException("Invalid email");
        }
        
        
        if(guest.getFirstName() != null){
            newGuest.setFirstName(guest.getFirstName());
        }
        if(guest.getLastName() != null){
            newGuest.setLastName(guest.getLastName());
        }
        if(guest.getEmail() != null){
            newGuest.setEmail(guest.getEmail());
        }

        if(guest.getExpireDate() != null){
            newGuest.setExpireDate(guest.getExpireDate());
        }
        
        return newGuest;
    }
    
    
    //RANDOM NUMBER METHOD
    public String randomNum() throws NoSuchAlgorithmException{
        String code;
        
        Random rnd = SecureRandom.getInstanceStrong();
        int number = rnd.nextInt(999999);
        code = String.format("%06d", number);

        return GUEST_INITIAL+code;
    }
    
    //FIND GUEST BY EMAIL
    public Guest findGuestByEmail(String email) throws NotFoundException{
        return guestRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("Guest with email "+email+" not Found"));
    }
    
    //FIND GUEST BY GUEST CODE
    public Guest findGuestByGuestCode(String code) throws NotFoundException{
        return guestRepository.findByGuestCode(code).orElseThrow(()-> new NotFoundException("Guest with code "+code+" not Found"));
    }
    
    //PRIVATE METHOD TO ADD ROOM LIST TO GUEST
    private Guest addRoomToGuest(Guest guest, RoomListDto rooms) throws NotFoundException{
          
        Iterator<Long> room = rooms.getRoomIds().iterator();
        
        while(room.hasNext()){
            Room newRoom = roomService.findById(room.next());
            
            newRoom.setStatus(ROOM_OCCUPIED);
            guest.addRoom(newRoom);
        }
        
        
        return guest;
    }
    
    //PRIVATE METHOD TO REMOVE ROOM FROM GUEST
    private Guest removeRoomFromGuest(Guest guest){
    
        guest.getRooms().stream().forEach(room ->{
            room.setStatus(ROOM_AVAILABLE);
            roomRepository.save(room);
        });       
        guest.getRooms().clear();
        
        return guestRepository.save(guest);
    }

    
}
