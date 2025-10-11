package com.userService.UserService.service;


import com.userService.UserService.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
@Getter
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKeyString; // This is where the value from application.properties is injected

    private SecretKey secretKey;

    private final long expiration = 3600000; // 1 hour

    @PostConstruct
    public void init() {
        System.out.println("--- JwtService Initialization ---");
        // Log the exact string value of the secret key as it's loaded
        System.out.println("Configured jwt.secret string (from @Value): '" + secretKeyString + "'");
        try {
            this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
            System.out.println("SecretKey derived from string successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing secret key in JwtService: " + e.getMessage());
            // It's good to throw an exception here if the key initialization fails
            throw new IllegalStateException("Failed to initialize JWT secret key", e);
        }
        System.out.println("---------------------------------");
    }

    public String generateToken(User user) {
        System.out.println("--- Token Generation ---");
        // Confirm the secret key used at generation time
        System.out.println("Generating token with secretKey derived from: '" + secretKeyString + "'");
        // Optional: Log token details before compacting (for debugging, be careful with sensitive data)
        // System.out.println("Claims: " + user.getEmail() + ", role: " + user.getRole().name());
        // System.out.println("Expiration: " + new Date(System.currentTimeMillis() + expiration));

        String generatedToken = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", "User")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        System.out.println("Generated Token (first 50 chars): " + generatedToken.substring(0, Math.min(generatedToken.length(), 50)) + "...");
        System.out.println("------------------------");
        return generatedToken;
    }



    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        final String email = extractEmail(token); // This implicitly calls extractClaim
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        System.out.println("--- Token Validation (Extracting Claims) ---");
        System.out.println("Token received (first 50 chars): " + token.substring(0, Math.min(token.length(), 50)) + "...");
        // Confirm the secret key used at validation time
        System.out.println("Using secretKey derived from: '" + secretKeyString + "' for parsing.");
        try {
            final Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token) // This is where the signature validation happens
                    .getBody();
            System.out.println("Token signature matched successfully.");
            return claimsResolver.apply(claims);
        } catch (SignatureException e) {
            System.err.println("JWT signature mismatch error: " + e.getMessage());
            // Log the problematic token and key for deeper analysis if needed
            // System.err.println("Problematic Token: " + token);
            // System.err.println("Key string used: '" + secretKeyString + "'");
            throw e; // Re-throw the exception so the calling code knows validation failed
        } catch (ExpiredJwtException e) {
            System.err.println("JWT expired: " + e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            System.err.println("Invalid JWT format: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("General JWT parsing error: " + e.getMessage());
            throw e;
        } finally {
            System.out.println("----------------------------------------");
        }
    }
}
