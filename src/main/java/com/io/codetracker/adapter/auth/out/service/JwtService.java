package com.io.codetracker.adapter.auth.out.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public final class JwtService {

    private final String SECRET_KEY;
    private final long jwtExpiration;

    public JwtService(@Value("${jwt.secret}") String SECRET_KEY, @Value("${jwt.expiration.ms}") long jwtExpiration) {
        this.SECRET_KEY = SECRET_KEY;
        this.jwtExpiration = jwtExpiration;
    }

    public String generateToken(String authId) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder().claims()
                .add(claims)
                .subject(authId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +  jwtExpiration))
                .and()
                .signWith(getKey())
                .compact();
    }

    public SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractAuthId(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String extractAuthId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
