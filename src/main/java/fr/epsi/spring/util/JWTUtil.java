package fr.epsi.spring.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {
    private static final String SECRET = "MaSuperCleSecrete1234567890123456";
    private Key key  = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    //private final String key = "monsecrettemporaire";
    private final long expiration = 1000 * 60 * 60;

    public JWTUtil(){
        byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /*private Key getSigningKey() {
        // convertit la string en Key pour HS256
        byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    } */

    public String generateToken (String username, String role){
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> validateToken(String token) throws JwtException {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public Map<String, Object> getClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return new HashMap<>(claims);
    } 

    public boolean isValid(String token) throws JwtException {
        try {
            this.validateToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
