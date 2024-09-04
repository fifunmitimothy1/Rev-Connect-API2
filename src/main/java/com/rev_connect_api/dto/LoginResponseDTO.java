package com.rev_connect_api.dto;

import java.util.Set;

import com.rev_connect_api.models.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private UserResponseDTO user;
    private String token;
}