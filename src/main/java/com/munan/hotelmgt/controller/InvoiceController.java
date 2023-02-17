package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.service.InvoiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoice")
@Tag(name = "Invoice Controller", description = "Invoice Controller")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
}
