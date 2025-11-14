package com.codelink.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;

@Component
public class JwtUtil {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private long jwtExpirationMs;

    private byte[] getSigningKeyBytes() {
        return jwtSecret.getBytes(StandardCharsets.UTF_8);
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(Keys.hmacShaKeyFor(getSigningKeyBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(getSigningKeyBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwt(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(getSigningKeyBytes()))
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String generateTokenFromUser(com.codelink.model.Usuario usuario) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("id", usuario.getId())
                .claim("role", usuario.getRole().name())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(Keys.hmacShaKeyFor(getSigningKeyBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getUserIdFromJwt(String token) {
        return ((Number) Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(getSigningKeyBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id")).longValue();
    }

    public String getRoleFromJwt(String token) {
        Object r = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(getSigningKeyBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role");
        return r == null ? null : r.toString();
    }   
}
