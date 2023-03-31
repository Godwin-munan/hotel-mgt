/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.dto;

import lombok.EqualsAndHashCode;
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
public class InvoiceDto {
    
    private Double lateCharges;
    
    private Double paymentTotal;
    
    private String guestCode;
    
}
