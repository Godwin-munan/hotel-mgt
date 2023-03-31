package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Gender;
import com.munan.hotelmgt.service.GenderService;
import com.munan.hotelmgt.utils.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gender")
@Tag(name = "Gender Controller", description = "Gender Controller")
@RequiredArgsConstructor
public class GenderController {


    private final GenderService genderService;

    //ADD
    @Operation(summary = "ADD GENDER", description = "Add a new gender")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<?>> addGender(@RequestBody Gender gender) throws AlreadyExistException {

        return genderService.addGender(gender);
    }

    
    //GET
    @Operation(summary = "GET ALL", description = "Retrieve all existing genders")
    @GetMapping("/get")
    public ResponseEntity<HttpResponse<?>> getAllGender() { return genderService.getAllGender();}

    @Operation(summary = "RETRIEVE BY ID", description = "Retrieve Gender by id")
    @GetMapping("/get/{gender_id}")
    public ResponseEntity<HttpResponse<?>> getByGenderId(@PathVariable(value = "gender_id") Long id) throws NotFoundException {
        return genderService.getById(id);
    }

    //DELETE
    @Operation(summary = "DELETE BY ID", description = "Delete existing gender by id")
    @DeleteMapping("/delete/{gender_id}")
    public ResponseEntity<HttpResponse<?>> deleteGenderById(@PathVariable("gender_id") Long id) throws NotFoundException, AlreadyExistException { return genderService.deleteGenderById(id);}

    //UPDATE
    @Operation(summary = "UPDATE GENDER", description = "Update existing gender by id")
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<?>> updateGender(@RequestBody Gender gender) {
        return genderService.updateGender(gender);
    }
}
