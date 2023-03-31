package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.dto.StaffDto;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.InvalidEmailException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Staff;
import com.munan.hotelmgt.service.StaffService;
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
@RequestMapping("/api/staff")
@Tag(name = "Staff Controller", description = "Staff Controller")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;
    
    //ADD
    @Operation(summary = "ADD STAFF", description = "Add a new staff")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<?>> addStaff(@RequestBody StaffDto staffDto) throws AlreadyExistException, NotFoundException, InvalidEmailException {
        return staffService.add(staffDto);
    }

    //GET
    @Operation(summary = "RETRIEVE ALL", description = "Retrieve all staffs by Pagination")
    @GetMapping("/get/{field}/{page}/{size}")
    public ResponseEntity<HttpResponse<?>> getAllStaffs(@PathVariable("field") String field,
                                                      @PathVariable("page") Integer page,
                                                      @PathVariable("size") Integer size) {
        return staffService.getAll(page, size, field);
    }

    @Operation(summary = "GET BY ID", description = "Retrieve staff by id")
    @GetMapping("/get/{staff_id}")
    public ResponseEntity<HttpResponse<?>> getStaffById(@PathVariable(value = "staff_id") Long id) throws NotFoundException {
        return staffService.getById(id);
    }
    
    @Operation(summary = "GET BY SHIFT ID", description = "Retrieve staff by shift id")
    @GetMapping("/get/shift/{shift_id}")
    public ResponseEntity<HttpResponse<?>> getStaffByShiftId(@PathVariable(value = "shift_id") Long id) throws NotFoundException {
        return staffService.getByShiftId(id);
    }
    
    @Operation(summary = "GET BY JOB ID", description = "Retrieve staff by Job id")
    @GetMapping("/get/job/{job_id}")
    public ResponseEntity<HttpResponse<?>> getStaffByJobId(@PathVariable(value = "job_id") Long id) throws NotFoundException {
        return staffService.getByJobId(id);
    }

    
    //DELETE
    @Operation(summary = "DELETE BY ID", description = "Remove single staff by id")
    @DeleteMapping("/delete/{staff_id}")
    public ResponseEntity<HttpResponse<?>> deleteStaffById(@PathVariable(value = "staff_id") Long id) throws NotFoundException {
        return staffService.deleteById(id);
    }
    
    
    //UPDATE
    @Operation(summary = "UPDATE STAFF", description = "Update staff record")
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<?>> updateStaff(@RequestBody Staff staff) throws NotFoundException, InvalidEmailException{
        return staffService.update(staff);
    }
}
