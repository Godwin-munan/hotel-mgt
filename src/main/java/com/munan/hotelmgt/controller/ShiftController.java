package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Shift;
import com.munan.hotelmgt.service.ShiftService;
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
@RequestMapping("/api/shift")
@Tag(name = "Shift Controller", description = "Shift Controller")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;
    
    //ADD
    @Operation(summary = "ADD SHIFT", description = "Add shift")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<?>> addJob(@RequestBody Shift shift) throws AlreadyExistException {
        return shiftService.addShift(shift);
    }

    //GET
    @Operation(summary = "RETRIEVE ALL SHIFTS", description = "Get all pagenated shift")
    @GetMapping("/get")
    public ResponseEntity<HttpResponse<?>> getAllShifts() {
        return shiftService.getAllShift();
    }

    @Operation(summary = "RETRIEVE SHIFT BY ID", description = "Retrjeve shift by id")
    @GetMapping("/get/{shift_id}")
    public ResponseEntity<HttpResponse<?>> getShiftById(@PathVariable(value = "shift_id") Long id) throws NotFoundException {
        return shiftService.getById(id);
    }

    
    //DELETE
    @Operation(summary = "DELETE SHIFT BY ID", description = "Delete shift by id")
    @DeleteMapping("/delete/{shift_id}")
    public ResponseEntity<HttpResponse<?>> deleteShiftById(@PathVariable(value = "shift_id") Long id) throws NotFoundException {
        return shiftService.deleteById(id);
    }
    
    
    //UPDATE
    @Operation(summary = "UPDATE SHIFT", description = "Update shift record")
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<?>> updateShift(@RequestBody Shift shift){
        return shiftService.update(shift);
    }
}
