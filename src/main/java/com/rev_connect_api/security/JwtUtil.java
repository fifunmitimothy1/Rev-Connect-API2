package com.rev_connect_api.security;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY = SIG.HS256.key().build();

    public SecretKey getSecretKey() {
        return SECRET_KEY;
    }

    public String generateToken(String username, Set<String> roles) {
        return Jwts.builder()
            .subject(username)              // add username as a claim
            .claim("roles", roles)     // add roles as a claim
            .issuedAt(new Date())           // set the issue date to the current time
                                            // set expiration time to 10 hours
            .expiration(new Date(System.currentTimeMillis() + 1000  * 60 * 60 * 10))
            .signWith(SECRET_KEY)           // sign the jwt with the specified key and H256 algorithm
            .compact();                     // compact the jwt to a url-safe string
    }

    /**
     * 
     * @param token The jwt token.
     * @return The username extracted from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 
     * @param token The jwt token.
     * @return The expiration date of the token.
     */
    public Date  extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 
     * @param <T> The type of the claim.
     * @param token The jwt token.
     * @param claimsResolver A function to resolve the claim.
     * @return The resolved claim. 
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token).getPayload();
        return claimsResolver.apply(claims);
    }

    /**
     * 
     * @param token The jwt token.
     * @return The jws containing the claims extracted from the token.
     */
    private Jws<Claims> extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(SECRET_KEY)             // verify the jwt's signature using the secret key
            .build()
            .parseSignedClaims(token);          // parse the claims in the signed jwt
    }

    /**
     * 
     * @param token The jwt token.
     * @return True if the token is expired, otherwise false
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 
     * @param token The jwt token.
     * @param username The username to validate against the token's signature.
     * @return True if the token is valid, otherwise false.
     */
    public Boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (username.equals(extractedUsername) && !isTokenExpired(token));
    }

    public Set<SimpleGrantedAuthority> extractRoles(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(SECRET_KEY)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) claims.get("roles");

        return roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
    }
}
