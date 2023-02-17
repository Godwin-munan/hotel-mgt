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

    @Operation(summary = "Add Gender", description = "Add a new gender")
    @PostMapping("/add/addGender")
    public ResponseEntity<HttpResponse<?>> add(@RequestBody Gender gender) throws AlreadyExistException {

        return genderService.addGender(gender);
    }

    @Operation(summary = "Get all genders", description = "Get all existing genders with sorted field")
    @GetMapping("/get/getAll")
    public ResponseEntity<HttpResponse<?>> getAll() { return genderService.getAllGender();}


    @Operation(summary = "Delete existing gender", description = "Delete existing gender by id")
    @DeleteMapping("/delete/deleteById/{id}")
    public ResponseEntity<HttpResponse<?>> deleteById(@PathVariable("id") Long id) throws NotFoundException, AlreadyExistException { return genderService.deleteGenderById(id);}

    @Operation(summary = "Update Gender")
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<?>> updateGender(@RequestBody Gender gender) {
        return genderService.updateGender(gender);
    }
}
