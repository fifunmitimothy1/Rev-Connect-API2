package com.rev_connect_api.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.dto.LoginRequestDTO;
import com.rev_connect_api.dto.LoginResponseDTO;
import com.rev_connect_api.security.JWTAuthenticationFilter;
import com.rev_connect_api.services.AuthService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginDTO) {
            logger.info("\n\nThis is the login in response: {}\n\n", loginDTO.toString());
            // authenticate the user and generate a jwt token
            LoginResponseDTO response = authService.loginUser(loginDTO);
            logger.info("\n\n response : {}\n\n", response);
            return ResponseEntity.ok(response);
    }
}
