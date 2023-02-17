package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "Payment Controller", description = "Payment Controller")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
}
