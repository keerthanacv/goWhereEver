package com.example.travelPlanner.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtil {

    private Logger LOGGER = LoggerFactory.getLogger(JWTUtil.class);

    private static final String SECRET_KEY = "b8e8f2a5c1d9e26c1b0a0f9d1e6e3e7c6f59c8d1f74c3e7d5a8d10e6fefb98e7";


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String userName)
    {
        LOGGER.info("In Generate Token Method");
        return generateToken(new HashMap<>(),userName);
    }

    public String generateToken(HashMap<String,Object> claims, String userName)
    {
        return Jwts.builder()
                .claims(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = (SECRET_KEY).getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        final String email = extractUserName(token);
        return (userDetails.getUsername().equals(email) && !isTokenExpired(token));
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver) // to extract only one required claim
    {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);  // to claim single one
    }

    public String extractUserName(String jwtToken) {

        return extractClaim(jwtToken,Claims::getSubject);  // the subject is mail/ username
    }




}
