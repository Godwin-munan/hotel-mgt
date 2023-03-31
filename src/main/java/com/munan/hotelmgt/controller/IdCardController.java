/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.IdCard;
import com.munan.hotelmgt.service.IdCardService;
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

/**
 *
 * @author godwi
 */

@RestController
@RequestMapping("/api/card")
@Tag(name = "Card Controller", description = "Card Controller")
@RequiredArgsConstructor
public class IdCardController {
    
    private final IdCardService cardService;
    
    //ADD
    @Operation(summary = "ADD CARD", description = "Add a new Card")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<?>> addCard(@RequestBody IdCard card) throws AlreadyExistException {
        return cardService.addCard(card);
    }

    //GET
    @Operation(summary = "GET ALL", description = "Retrieve all Cards")
    @GetMapping("/get")
    public ResponseEntity<HttpResponse<?>> getAllCards() throws NotFoundException {
        return cardService.getAll();
    }
    
    @Operation(summary = "GET BY ID", description = "Retrieve existing Card by id")
    @GetMapping("/get/{card_id}")
    public ResponseEntity<HttpResponse<?>> getCardById(@PathVariable(value = "card_id") Long id) throws NotFoundException {
        return cardService.getById(id);
    }

    
    //DELETE
    @Operation(summary = "DELETE BY ID", description = "Remove single Card by id")
    @DeleteMapping("/delete/{card_id}")
    public ResponseEntity<HttpResponse<?>> deleteCardById(@PathVariable(value = "card_id") Long id) throws NotFoundException {
        return cardService.deleteById(id);
    }
    
    
    //UPDATE
    @Operation(summary = "UPDATE CARD", description = "Update card record")
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<?>> updateCard(@RequestBody IdCard card){
        return cardService.update(card);
    }
    
    
}
