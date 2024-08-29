package com.rev_connect_api.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

class JwtUtilTest {
    
    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    void setup() {
        // init Mockito and inject mocks into the JwtUtil class
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGeneratToken() {
        String username = "john_snow";
        String token = jwtUtil.generateToken(username, null);

        // parse the gerneated token to verify the claims
        Jws<Claims> claimJws = Jwts.parser()
            .verifyWith(jwtUtil.getSecretKey())
            .build()
            .parseSignedClaims(token);
        
        assertEquals(username, claimJws.getPayload().getSubject());
    }
    @Test
    void testExtractUsername() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username, null);

        // Extract the username from the token and assert that it matches the expected value
        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void testExtractExpiration() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username, null);

        // Extract the expiration date from the token and assert that it is after the current time
        Date expiration = jwtUtil.extractExpiration(token);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void testValidateToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username, null);

        // Validate the token by comparing the username and checking expiration
        assertTrue(jwtUtil.validateToken(token, username));
    }

    @Test
    void testIsTokenExpired() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username, null);

        // Ensure that the token is not expired immediately after generation
        assertTrue(!jwtUtil.isTokenExpired(token));
    }
}
