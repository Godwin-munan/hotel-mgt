package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.dto.LoginDto;
import com.munan.hotelmgt.service.AppUserService;
import com.munan.hotelmgt.utils.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "Authentication Controller")
@RequiredArgsConstructor
public class AuthController {
    private final AppUserService userService;

    @Operation(summary = "Login with credential", description = "Login to get authorization token")
    @PostMapping("/login")
    public ResponseEntity<HttpResponse<?>> getToken(@RequestBody LoginDto login){
       return userService.getJwtToken(login);

    }
}
