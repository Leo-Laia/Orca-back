package com.orca.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.Date;

@Component
public class JwtTokenProvider {

    Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expire-length}")
    private long validityInMilliseconds;

    private Key key;

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createToken(String username, Collection<String> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("ORCA :\n" + e.getMessage() );
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Collection<String> getRoles(String token) {
        return (Collection<String>) Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles");
    }
}
