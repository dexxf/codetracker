package com.io.codetracker.adapter.auth.out.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public final class JwtService {

    private static String SECRET_KEY;

    static {  // Secret key is generated at runtime for testing and demo purposes only
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();
        SECRET_KEY = Encoders.BASE64.encode(keyBytes);
    }

    public String generateToken(String authId) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder().claims()
                .add(claims)
                .subject(authId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +  1000 * 60 * 60))
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
        return extractClaim(token, e -> e.getSubject());
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
        return extractClaim(token, e -> e.getExpiration()).before(new Date());
    }

}
