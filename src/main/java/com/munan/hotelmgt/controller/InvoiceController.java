package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.dto.InvoiceDto;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Invoice;
import com.munan.hotelmgt.service.InvoiceService;
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
@RequestMapping("/api/invoice")
@Tag(name = "Invoice Controller", description = "Invoice Controller")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    
    //ADD
    @Operation(summary = "ADD INVOICE", description = "Add a new Invoice")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse<?>> addInvoice(@RequestBody InvoiceDto invoiceDto) throws AlreadyExistException, NotFoundException, Exception {
        return invoiceService.add(invoiceDto);
    }
    

    //GET
    @Operation(summary = "RETRIEVE ALL", description = "Retrieve all Invoices by Pagination")
    @GetMapping("/get/{field}/{page}/{size}")
    public ResponseEntity<HttpResponse<?>> getAllInvoices(@PathVariable("field") String field,
                                                      @PathVariable("page") Integer page,
                                                      @PathVariable("size") Integer size) {
        return invoiceService.getAll(page, size, field);
    }

    @Operation(summary = "GET BY ID", description = "Retrieve Invoice by id")
    @GetMapping("/get/{invoice_id}")
    public ResponseEntity<HttpResponse<?>> getInvoiceById(@PathVariable(value = "invoice_id") Long id) throws NotFoundException {
        return invoiceService.getById(id);
    }
    
    @Operation(summary = "GET BY GUEST ID", description = "Retrieve Invoice by Guest id")
    @GetMapping("/get/guest/{guest_id}")
    public ResponseEntity<HttpResponse<?>> getInvoiceByGuestId(@PathVariable(value = "guest_id") Long id) throws NotFoundException {
        return invoiceService.getByGuestId(id);
    }

    
    //DELETE
    @Operation(summary = "DELETE BY ID", description = "Remove single Invoice by id")
    @DeleteMapping("/delete/{invoice_id}")
    public ResponseEntity<HttpResponse<?>> deleteInvoiceById(@PathVariable(value = "invoice_id") Long id) throws NotFoundException {
        return invoiceService.deleteById(id);
    }
    
    
    //UPDATE
    @Operation(summary = "UPDATE LATE CHARGES", description = "Update Invoice record, Use to update late charges only")
    @PutMapping("/update")
    public ResponseEntity<HttpResponse<?>> updateInvoice(@RequestBody Invoice guest) throws NotFoundException{
        return invoiceService.update(guest);
    }
}
