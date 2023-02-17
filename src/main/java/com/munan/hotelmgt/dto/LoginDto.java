package com.munan.hotelmgt.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class LoginDto{

    private String username;
    private String password;
}
