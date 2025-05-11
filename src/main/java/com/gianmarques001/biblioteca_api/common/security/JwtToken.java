package com.gianmarques001.biblioteca_api.common.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtToken {
    private String token;
}
