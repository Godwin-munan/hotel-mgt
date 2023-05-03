package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.dto.PaymentDto;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Payment;
import com.munan.hotelmgt.service.PaymentService;
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
@RequestMapping("/api/payment")
@Tag(name = "Payment Controller", description = "Payment Controller")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    
    //ADD
    @Operation(summary = "ADD PAYMENT", description = "Add a new Payment to an Invoice")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<?>> addPayment(@RequestBody PaymentDto paymentDto) throws AlreadyExistException, NotFoundException{
        return paymentService.add(paymentDto);
    }
    
    @Operation(summary = "ADD PAYMENT BY INVOICE ID", description = "Add a new Payment by invoice id, ommit invoice code")
    @PostMapping("/add/{invoice_id}")
    public ResponseEntity<HttpResponse<?>> addPaymentByInvoiceId(@PathVariable(value = "invoice_id") Long id, @RequestBody PaymentDto paymentDto) throws AlreadyExistException, NotFoundException{
        return paymentService.addByInvoiceId(id, paymentDto);
    }
    
    //GET
    @Operation(summary = "GET BY INVOICE ID", description = "Retrieve Payment by Invoice id")
    @GetMapping("/get/{invoice_id}")
    public ResponseEntity<HttpResponse<?>> getPaymentByInvoiceId(@PathVariable(value = "invoice_id") Long id) throws NotFoundException {
        return paymentService.getByInvoiceId(id);
    }
    
    @Operation(summary = "GET BY INVOICE CODE", description = "Retrieve Payment by Invoice code")
    @GetMapping("/get/code/{invoice_code}")
    public ResponseEntity<HttpResponse<?>> getPaymentByInvoiceCode(@PathVariable(value = "invoice_code") String code) throws NotFoundException {
        return paymentService.getByInvoiceCode(code);
    }
    
    @Operation(summary = "GET BY GUEST CODE", description = "Retrieve Payment by Guest code")
    @GetMapping("/get/guestCode/{guest_code}")
    public ResponseEntity<HttpResponse<?>> getPaymentByGuestCode(@PathVariable(value = "guest_code") String code) throws NotFoundException {
        return paymentService.getByGuestCode(code);
    }
    
    @Operation(summary = "GET BY GUEST EMAIL", description = "Retrieve Payment by Guest email")
    @GetMapping("/get/guestEmail/{guest_email}")
    public ResponseEntity<HttpResponse<?>> getPaymentByGuestEmail(@PathVariable(value = "guest_email") String email) throws NotFoundException {
        return paymentService.getByGuestEmail(email);
    }
    
    @Operation(summary = "RETRIEVE ALL", description = "Retrieve all payments by Pagination")
    @GetMapping("/get/{field}/{page}/{size}")
    public ResponseEntity<HttpResponse<?>> getAllPayment(@PathVariable("field") String field,
                                                      @PathVariable("page") Integer page,
                                                      @PathVariable("size") Integer size) {
        return paymentService.getAll(page, size, field);
    }
    
    //DELETE
    @Operation(summary = "REMOVE PAYMENT BY ID", description = "Remove payment by id")
    @DeleteMapping("/remove/{payment_id}")
    public ResponseEntity<HttpResponse<?>> removePaymentByPaymentId(@PathVariable(value = "payment_id") Long id) throws NotFoundException {
        return paymentService.removeById(id);
    }
    
    //UPDATE 
    @Operation(summary = "UPDATE PAYMENT", description = "Update payment")
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<?>> updatePayment(@RequestBody Payment payment) throws NotFoundException{
        return paymentService.update(payment);
    }
}
