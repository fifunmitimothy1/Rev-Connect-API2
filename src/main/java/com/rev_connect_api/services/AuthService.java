package com.rev_connect_api.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rev_connect_api.dto.LoginRequestDTO;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;
import com.rev_connect_api.security.JwtUtil;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthService(UserService userService, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 
     * @param loginRequestDTO The DTO containing the user's login credentials.
     * @return A JWT token if authentication is successful.
     * @throws ResponseStatusException if the username or password is incorrect, 401 status.
     */
    public String loginUser(LoginRequestDTO loginRequestDTO) {
        logger.info("Attempting to authenticate user: {}", loginRequestDTO.getUsername());
        
        User user = userService.findUserByUsername(loginRequestDTO.getUsername());
        // check if the users exists and password matches the store hashed password
        if (user != null && passwordEncoder.matches(loginRequestDTO.getPassword(),user.getPassword())) {
            logger.info("Authentication successful for user: {}", loginRequestDTO.getUsername());
            Set<String> roles = user.getRoles().stream()
                .map(Role::name)
                .collect(Collectors.toSet());
                
            return jwtUtil.generateToken(user.getUsername(), roles);
        } else { 
            logger.info("Authentication failed for user: {}", loginRequestDTO.getUsername());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }
}
