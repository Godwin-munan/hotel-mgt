/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.service;

import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.IdCard;
import com.munan.hotelmgt.model.Role;
import com.munan.hotelmgt.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author godwi
 */

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    
    private final RoleRepository roleRepository;
    
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
