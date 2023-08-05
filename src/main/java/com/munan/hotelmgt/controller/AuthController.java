package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.dto.LoginDto;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.service.AuthService;
import com.munan.hotelmgt.utils.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
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
    private final AuthService authService;

    @Operation(summary = "Login with credential", description = "Login to get authorization token")
//    @Operation(summary = "Login with credential",
//        parameters = {
//            @Parameter(in = ParameterIn.HEADER, name = "authorization",
//                description = "Authorization (Bearer token)", required = true,
//                schema = @Schema(type = "string")),         
//            })
    @PostMapping("/login")
    public ResponseEntity<HttpResponse<?>> getToken(@RequestBody LoginDto login) throws NotFoundException{
       return authService.getJwtToken(login);

    }
}
