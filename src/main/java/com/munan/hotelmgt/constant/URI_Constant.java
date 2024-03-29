package com.munan.hotelmgt.constant;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class URI_Constant {
    public static final String AUTH_URL = "/api/auth/authenticate";
    public static final String REFRESH_URL = "/api/auth/refresh";

    public static URI getURL(String stringPath) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(stringPath).toString());
    }


}

