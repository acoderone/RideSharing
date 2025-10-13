package com.Driver.DriverService.service;

import com.Driver.DriverService.model.Rider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
@Getter
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKeyString;

    private SecretKey secretKey;

    private final long expiration=3600000;

    @PostConstruct
    public void init(){
        System.out.println("----JwtService Initialization------");
        try{
            this.secretKey= Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
        }
        catch(Exception e){
            System.err.println("Error initilaizing secret key in JwtService: "+e.getMessage());
            throw new IllegalStateException("Failed to initialize the JWT secret key",e);
        }
    }

    public String generateToken(Rider rider) {
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

}
