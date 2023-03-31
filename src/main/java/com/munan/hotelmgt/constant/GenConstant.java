/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.constant;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.NoArgsConstructor;

/**
 *
 * @author godwi
 */
@NoArgsConstructor
public class GenConstant {
    public static final String succesResponse = "Successfull";
    public static final String INVOICE_INITIAL = "INV";
    public static final String GUEST_INITIAL = "GT";
    
    //ROOM
    public static final String ROOM_OCCUPIED ="occupied";
    public static final String ROOM_AVAILABLE ="available";
    
    
    /* EMAIL 
        email validation field method
    */
    public static final String EMAIL_PATTERN = "[A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            +"[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    public static boolean emailValidator(String email)
    {
         Pattern pattern;
         Matcher matcher;

        pattern = Pattern.compile(EMAIL_PATTERN);

        matcher = pattern.matcher(email);

        return matcher.matches();

    }
    
}
