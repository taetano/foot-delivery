package com.project.delivery.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtProvider {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    private final SecretKey secretKey;

    private final Logger log = Logger.getLogger(this.getClass().getName());

    public JwtProvider() {
        byte[] keys = KeyGenerators.secureRandom(256).generateKey();
        this.secretKey = Keys.hmacShaKeyFor(keys);
    }

    public String createToken(String username) {
        Date currentTime = new Date();

        return Jwts.builder()
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(currentTime)
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Optional<String> resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return Optional.of(bearerToken.substring(7));
        }

        return Optional.empty();
    }

    public Long getUserIdFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("id", Long.class));
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public long getExpiration(String token) {
        return getClaimFromToken(token, Claims::getExpiration).getTime() - new Date().getTime();
    }

    public void validationToken(String token) throws ExpiredJwtException {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            log.info("유효하지 않은 JWT 서명 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않은 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 토큰 입니다.");
        }
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaims(token);
        return claimResolver.apply(claims);
    }

    public boolean validateExpire(String refreshToken) {
        long time = getClaimFromToken(refreshToken, Claims::getExpiration).getTime() - new Date().getTime();
        return time <= 0;
    }

    public void processExpire(String token) {
        getAllClaims(token).setExpiration(new Date());
    }
}
