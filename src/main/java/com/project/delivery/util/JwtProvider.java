package com.project.delivery.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    private final SecretKey secretKey;

//    @Value("${service.jwt.secret-key}") String secretKey
    public JwtProvider() {
        byte[] keys = KeyGenerators.secureRandom(256).generateKey();
        this.secretKey = Keys.hmacShaKeyFor(keys);
    }

    public String createToken(String username) {
        Date currentTime = new Date();

        return Jwts.builder()
                .claim("email", username)
                .setIssuer(issuer)
                .setIssuedAt(currentTime)
                .setExpiration(new Date(currentTime.getTime() + accessExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
