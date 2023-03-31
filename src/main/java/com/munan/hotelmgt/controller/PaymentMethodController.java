/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.PaymentMethod;
import com.munan.hotelmgt.service.PaymentMethodService;
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
@RequestMapping("/api/paymethod")
@Tag(name = "Payment Method Controller", description = "Payment Method Controller")
@RequiredArgsConstructor
public class PaymentMethodController {
    
    private final PaymentMethodService methodService;
    
    //ADD
    @Operation(summary = "ADD PAYMENT METHOD", description = "Add a new Payment Method")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<?>> addPayMethod(@RequestBody PaymentMethod method) throws AlreadyExistException {
        return methodService.add(method);
    }

    
    //GET
    @Operation(summary = "GET ALL", description = "Retrieve all existing Payment Methods")
    @GetMapping("/get")
    public ResponseEntity<HttpResponse<?>> getAllPayMethods() { return methodService.getAll();}

    @Operation(summary = "RETRIEVE BY ID", description = "Retrieve Payment Method by id")
    @GetMapping("/get/{payMethod_id}")
    public ResponseEntity<HttpResponse<?>> getByPayMethodId(@PathVariable(value = "payMethod_id") Long id) throws NotFoundException {
        return methodService.getById(id);
    }

    //DELETE
    @Operation(summary = "DELETE BY ID", description = "Delete existing Payment Method by id")
    @DeleteMapping("/delete/{payMethod_id}")
    public ResponseEntity<HttpResponse<?>> deletePayMethodById(@PathVariable(value = "payMethod_id") Long id) throws NotFoundException, AlreadyExistException {
        return methodService.deleteById(id);
    }

    //UPDATE
    @Operation(summary = "UPDATE GENDER", description = "Update existing Payment Method")
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<?>> updatePayMethod(@RequestBody PaymentMethod method) {
        return methodService.update(method);
    }
    
}
