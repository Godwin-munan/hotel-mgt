/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    
    private String genderType;
}
