package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.dto.CheckoutDto;
import com.munan.hotelmgt.dto.GuestDto;
import com.munan.hotelmgt.dto.RoomListDto;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.InvalidEmailException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Guest;
import com.munan.hotelmgt.service.GuestService;
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
@RequestMapping("/api/guest")
@Tag(name = "Guest Controller", description = "Guest Controller")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;
    
    //ADD
    @Operation(summary = "ADD GUEST", description = "Add a new guest")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<?>> addGuest(@RequestBody GuestDto guestDto) throws AlreadyExistException, NotFoundException, Exception, InvalidEmailException {
        return guestService.add(guestDto);
    }
    
    
    @Operation(summary = "ADD ROOMS TO GUEST", description = "Add a rooms to guests")
    @PostMapping("/add/room/{guest_id}")
    public ResponseEntity<HttpResponse<?>> addRoomsToGuest(@PathVariable(value = "guest_id") Long stId, @RequestBody RoomListDto roomList) throws AlreadyExistException, NotFoundException, Exception {
        return guestService.addRoomList(stId, roomList);
    }
    
    @Operation(summary = "CHECK OUT GUEST", description = "Check guest out with check out date")
    @PostMapping("/checkout")
    public ResponseEntity<HttpResponse<?>> checkGuestOut(@RequestBody CheckoutDto checkDto) throws AlreadyExistException, NotFoundException, Exception {
        return guestService.checkOut(checkDto);
    }

    //GET
    @Operation(summary = "RETRIEVE ALL", description = "Retrieve all guests by Pagination")
    @GetMapping("/get/{field}/{page}/{size}")
    public ResponseEntity<HttpResponse<?>> getAllGuests(@PathVariable("field") String field,
                                                      @PathVariable("page") Integer page,
                                                      @PathVariable("size") Integer size) {
        return guestService.getAll(page, size, field);
    }
    
    @Operation(summary = "GET CURRENT GUEST COUNT", description = "Retrieve number of current guest")
    @GetMapping("/get/currentCount")
    public ResponseEntity<HttpResponse<?>> getCurrentGuestCount() throws NotFoundException {
        return guestService.currentGuestCount();
    }
    
    @Operation(summary = "GET CURRENT GUESTS", description = "Retrieve all current guest")
    @GetMapping("/get/current")
    public ResponseEntity<HttpResponse<?>> getCurrentGuest() throws NotFoundException {
        return guestService.currentGuest();
    }

    @Operation(summary = "GET BY ID", description = "Retrieve guest by id")
    @GetMapping("/get/{guest_id}")
    public ResponseEntity<HttpResponse<?>> getGuestById(@PathVariable(value = "guest_id") Long id) throws NotFoundException {
        return guestService.getById(id);
    }

    
    //DELETE
    @Operation(summary = "DELETE BY ID", description = "Remove single guest by id")
    @DeleteMapping("/delete/{guest_id}")
    public ResponseEntity<HttpResponse<?>> deleteGuestById(@PathVariable(value = "guest_id") Long id) throws NotFoundException {
        return guestService.deleteById(id);
    }
    
    
    //UPDATE
    @Operation(summary = "UPDATE GUEST", description = "Update guest record")
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<?>> updateGuest(@RequestBody Guest guest) throws NotFoundException, InvalidEmailException{
        return guestService.update(guest);
    }
}
