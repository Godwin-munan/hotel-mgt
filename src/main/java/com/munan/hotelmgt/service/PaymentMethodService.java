/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.service;

import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.PaymentMethod;
import com.munan.hotelmgt.repository.PaymentMethodRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author godwi
 */

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentMethodService {
    
    private final PaymentMethodRepository methodRepository;

    //ADD NEW PAYMENT METHOD
    public ResponseEntity<HttpResponse<?>> add(PaymentMethod method) throws AlreadyExistException {
        Optional<PaymentMethod> existingMethod = methodRepository.findByType(method.getType());

        if(existingMethod.isPresent()){
            throw new AlreadyExistException("Payment Method already exist");
        }

        PaymentMethod newMethod = new PaymentMethod();
        newMethod.setType(method.getType());

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        methodRepository.save(newMethod)
                ));
    }

    //GET ALL PAYMENT METHODS
    public ResponseEntity<HttpResponse<?>> getAll() {
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        methodRepository.findAll())
        );
    }

    //GET PAYMENT METHOD BY ID
    public ResponseEntity<HttpResponse<?>> getById(Long id) throws NotFoundException {
        PaymentMethod findMethod = findById(id);

        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        findMethod
                ));
    }

    //DELETE PAYMENT BY ID
    public ResponseEntity<HttpResponse<?>> deleteById(Long id) throws NotFoundException {
        PaymentMethod findMethod = findById(id);

        String type = findMethod.getType();

        methodRepository.delete(findMethod);

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        "Payment Type " + type +" has been deleted"
                ));
    }

    //UPDATE PAYMENT METHOD
    public ResponseEntity<HttpResponse<?>> update(PaymentMethod method) {
        PaymentMethod savedMethod = new PaymentMethod();
        
        if(method.getId() != null){
            savedMethod.setId( method.getId());
        }
        
        savedMethod.setType(method.getType());
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        methodRepository.save(savedMethod)
                )); 
    }
    
    //PRIVATE METHOD TO FIND BY ID
    private PaymentMethod findById(Long id) throws NotFoundException {
        return methodRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Payment Method with id "+id+" Does not exist"));

    }
    
    //PRIVATE METHOD TO FIND BY TYPE
    public PaymentMethod findByType(String type) throws NotFoundException {
        return methodRepository.findByType(type)
                .orElseThrow(()->new NotFoundException("Payment Method "+type+" Does not exist"));

    }
}
