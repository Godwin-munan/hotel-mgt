/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.dto;

import java.util.ArrayList;
import lombok.EqualsAndHashCode;
import com.munan.hotelmgt.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author godwi
 */

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class UserDto {
    
    private String firstName;
    
    private String lastName;
    
    private String username;
    
    private String password;

    private ArrayList<Role> roles;
    
}
