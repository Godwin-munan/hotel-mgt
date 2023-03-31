/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.dto;

import com.munan.hotelmgt.model.Role;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author godwi
 */

@Data
@NoArgsConstructor
public class RoleListDto {
    private ArrayList<Role> roles;
}
