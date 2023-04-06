/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.service;

import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.dto.LoginDto;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.AppUser;
import com.munan.hotelmgt.repository.AppUserRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import com.munan.hotelmgt.utils.Token;
import jakarta.transaction.Transactional;
import java.time.Instant;
import static java.time.temporal.ChronoUnit.HOURS;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

/**
 *
 * @author godwi
 */

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder encoder;
    private final AppUserRepository userRepository;

    //GET JWT TOKEN
    public ResponseEntity<HttpResponse<?>> getJwtToken(LoginDto login) throws NotFoundException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
        );
        
        
        Instant now = Instant.now();
        List<String> scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
       
        
        
        
        AppUser user = userRepository.findByUsername(authentication.getName()).orElseThrow(()-> new NotFoundException("Not Found"));
        

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("hotelMgt")
                .issuedAt(now)
                .expiresAt(now.plus(10, HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("first", user.getFirstName())
                .claim("last", user.getLastName())
                .build();

        JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(claims);

        String token = this.encoder.encode(encoderParameters).getTokenValue();


        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(), 
                        HttpStatus.OK, 
                        succesResponse,
                        new Token(token,null))
        );
    }
    
}
