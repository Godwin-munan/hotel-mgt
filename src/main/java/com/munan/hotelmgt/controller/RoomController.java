package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room")
@Tag(name = "Room Controller", description = "Room Controller")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
}
