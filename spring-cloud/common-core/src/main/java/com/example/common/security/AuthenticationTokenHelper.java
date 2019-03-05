package com.example.common.security;

import static com.example.common.constants.WebSecurityConstant.ACCESS_TOKEN_EXPIRATION;
import static com.example.common.constants.WebSecurityConstant.TOKEN_SECRET_KEY;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthenticationTokenHelper {

    public String getUsernameFromToken(String token) {
        String username = null;

        try {
            final Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }

        return username;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(TOKEN_SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }

        return claims;
    }

    public String generateToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setIssuedAt(generateCurrentDate())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET_KEY)
                .compact();
        return token;
    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION * 1000);
    }
}
