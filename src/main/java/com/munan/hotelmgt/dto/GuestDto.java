/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author godwi
 */

@Data
@NoArgsConstructor
public class GuestDto {
   
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private LocalDate checkIn;
    
    private LocalDate expireDate;
    
    private Long genderId;
}
