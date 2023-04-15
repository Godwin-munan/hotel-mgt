/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.dto;

import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author godwi
 */

@Data
@NoArgsConstructor
public class RoomListDto {
    
    private ArrayList<Long> roomIds;
}
