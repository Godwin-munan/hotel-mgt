package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.service.GuestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guest")
@Tag(name = "Guest Controller", description = "Guest Controller")
@RequiredArgsConstructor
public class GuestController {

    private GuestService guestService;
}
