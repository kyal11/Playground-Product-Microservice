package com.microservice.product.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class JwtConfig {
    @Value("${jwt.secret}")
    private String jwtSecret;
}
