package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.service.BillingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
@Tag(name = "Billing Controller", description = "Billing Controller")
public class BillingController {

    private final BillingService billingService;

}
