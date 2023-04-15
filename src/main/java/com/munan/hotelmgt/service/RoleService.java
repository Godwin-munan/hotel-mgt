/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.service;

import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Role;
import com.munan.hotelmgt.repository.RoleRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author godwi
 */

@Service
@Transactional
public class RoleService {
    
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    
    //GET ALL ROLES
    public ResponseEntity<HttpResponse<?>> getAll() {
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        roleRepository.findAll()
                )
        );
    }
    
    //FIND CARD BY TYPE
    public Role findRoleByType(String name)throws NotFoundException{
        return roleRepository.findByName(name).orElseThrow(()-> new NotFoundException("Role "+name+" not Found"));
    }
    
    //METHOD TO FIND BY ID
    public Role findById(Long id) throws NotFoundException {

        return roleRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Shift with id "+id+", Does not exist"));

    }

    
}
