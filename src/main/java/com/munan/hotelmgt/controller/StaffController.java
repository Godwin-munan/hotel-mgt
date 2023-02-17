package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.service.StaffService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/staff")
@Tag(name = "Staff Controller", description = "Staff Controller")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;
}
