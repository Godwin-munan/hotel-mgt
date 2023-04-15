/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.controller;

import com.munan.hotelmgt.service.RoleService;
import com.munan.hotelmgt.utils.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author godwi
 */
@RestController
@RequestMapping("/api/role")
@Tag(name = "Role Controller", description = "Role Controller")
public class RoleController {
    
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    //GET
    @Operation(summary = "RETRIEVE ALL", description = "Retrieve all users")
    @GetMapping("/get")
    public ResponseEntity<HttpResponse<?>> getAllRoles() {
        return roleService.getAll();
    }
    
   
    
}
