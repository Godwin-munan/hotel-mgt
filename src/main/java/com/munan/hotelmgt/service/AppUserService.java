package com.munan.hotelmgt.service;

import com.munan.hotelmgt.dto.LoginDto;
import com.munan.hotelmgt.repository.AppUserRepository;
import com.munan.hotelmgt.repository.RoleRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import com.munan.hotelmgt.utils.Token;
import jakarta.transaction.Transactional;
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

import java.time.Instant;
import java.util.List;
import static java.time.temporal.ChronoUnit.HOURS;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder encoder;


    //GET JWT TOKEN
    public ResponseEntity<HttpResponse<?>> getJwtToken(LoginDto login) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
        );

        Instant now = Instant.now();
        List<String> scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("aud", List.of("Gtee"))
                .build();

        JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(claims);

        String token = this.encoder.encode(encoderParameters).getTokenValue();


        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(), "OK", new Token(token,null))
        );
    }
}
