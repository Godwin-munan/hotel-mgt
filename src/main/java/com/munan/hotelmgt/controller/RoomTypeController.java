package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.service.RoomTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room_type")
@Tag(name = "Room Type Controller", description = "Room Type Controller")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;
}
