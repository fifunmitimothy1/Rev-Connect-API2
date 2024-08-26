package com.rev_connect_api.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rev_connect_api.models.User;
import com.rev_connect_api.services.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Lazy
    private final UserService userService;

    public JWTAuthenticationFilter(JwtUtil jwtUtil, @Lazy UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        
        final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // check if the authorization header is present and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // jwt = authorizationHeader.substring(7); // extract the jwt from the header
            // username = jwtUtil.extractUsername(jwt);           // extract the username from the jwt
            // logger.info("JWT extracted: {}", jwt);
            // logger.info("Username extracted from JWT: {}", username);
            jwt = authorizationHeader.substring(7);
            logger.info("Extracted JWT: {}", jwt);
            try {
                username = jwtUtil.extractUsername(jwt);
                logger.info("Extracted username from JWT: {}", username);
            } catch (Exception e) {
                logger.error("Error extracting username from JWT: {}", e.getMessage());
            }
        } else {
            logger.warn("Invalid Authorization header: {}", authorizationHeader);
        
        }

        // check if the username is not null and if the user is not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = userService.findUserByUsername(username);
            var authorities = jwtUtil.extractRoles(jwt);

            logger.info("\n\nUser is like :  {}\n\n", user);
            logger.info("\n\nAuthorities is like :  {}\n\n", authorities);
            
            // validate the token against the user details
            if (jwtUtil.validateToken(jwt, user.getUsername())) {
                // create an authentication token and set it in the security context

                Principal principal = new Principal(user.getUserId(), user.getUsername());
                UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(principal, null, authorities);

                    logger.info("\n\n authToken: {}\n\n", authenticationToken);

                    authenticationToken.setDetails(new  WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }

}
