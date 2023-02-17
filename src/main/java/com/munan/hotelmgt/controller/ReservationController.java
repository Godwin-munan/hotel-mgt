package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.service.ReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
@Tag(name = "Reservation Controller", description = "Reservation Controller")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
}
