package kz.javazhan.sigma_finance.components;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtAccessTokenExpirationMs;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long jwtRefreshTokenExpirationMs;

    private Key signingKey;


    @PostConstruct
    public void init(){
        if (secretKey == null){
            throw new IllegalStateException("JWT secret key is not set!");
        }
        if (jwtRefreshTokenExpirationMs == 0 && jwtAccessTokenExpirationMs == 0){
            throw new IllegalStateException("Expiration miliSecond is not set!");
        }
        // Для HS512 требуется ключ >= 64 байт; используем один секрет для HS256 и HS512
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 64) {
            throw new IllegalStateException("JWT secret key must be at least 64 bytes for HS512 (refresh tokens). Current length: " + keyBytes.length + " bytes");
        }
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtAccessTokenExpirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshTokenExpirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }


    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractEmail(token);
        return (extractedUsername.equals(username)) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
