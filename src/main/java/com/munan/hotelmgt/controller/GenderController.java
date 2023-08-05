package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Gender;
import com.munan.hotelmgt.service.GenderService;
import com.munan.hotelmgt.utils.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    
    @PostMapping("/add")
    @Operation(summary = "ADD GENDER", description = "Add a new gender")
    public ResponseEntity<HttpResponse<?>> addGender(@RequestBody Gender gender) throws AlreadyExistException {

        return genderService.addGender(gender);
    }

    
    //GET
    @Operation(summary = "GET ALL", description = "Retrieve all existing genders")
    @GetMapping("/get")
//    @Operation(summary = "GET ALL", description = "Retrieve all existing genders",
//        parameters = {
//            @Parameter(in = ParameterIn.HEADER, name = "Authorization",
//                description = "Authorization (Bearer token)", required = true,
//                schema = @Schema(type = "string")),})
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "401", description = "Unauthenticated")
//    })
    public ResponseEntity<HttpResponse<?>> getAllGender() { return genderService.getAllGender();}

    
    @GetMapping("/get/{gender_id}")
    @Operation(summary = "RETRIEVE BY ID", description = "Retrieve Gender by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthenticated")
    })
    public ResponseEntity<HttpResponse<?>> getByGenderId(@PathVariable(value = "gender_id") Long id) throws NotFoundException {
        return genderService.getById(id);
    }

    //DELETE
    
    @DeleteMapping("/delete/{gender_id}")
    @Operation(summary = "DELETE BY ID", description = "Delete existing gender by id")
    public ResponseEntity<HttpResponse<?>> deleteGenderById(@PathVariable("gender_id") Long id) throws NotFoundException, AlreadyExistException { return genderService.deleteGenderById(id);}

    //UPDATE
    
    @PutMapping("/update")
    @Operation(summary = "UPDATE GENDER", description = "Update existing gender by id")
    public ResponseEntity<HttpResponse<?>> updateGender(@RequestBody Gender gender) {
        return genderService.updateGender(gender);
    }
}
