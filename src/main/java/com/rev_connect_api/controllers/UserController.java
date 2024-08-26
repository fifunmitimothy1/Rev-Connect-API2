package com.rev_connect_api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.dto.UserRegistrationDTO;
import com.rev_connect_api.dto.UserResponseDTO;
import com.rev_connect_api.dto.UserUpdateDTO;
import com.rev_connect_api.models.User;
import com.rev_connect_api.services.UserService;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    
    private final UserService userService;
 
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRegistrationDTO registrationDTO) {
        // handle registrattion logic in UserService
        User user = userService.registerUser(mapToEntity(registrationDTO));
        UserResponseDTO responseDTO = mapToDTO(user);
        // TODO: send a verification email
        // emailService.sendVerficiationEmail(user);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(mapToDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO updateDTO) {
        User user = userService.updateUser(id, updateDTO);
        return ResponseEntity.ok(mapToDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> responseDTOs = users.stream().map(this::mapToDTO)
            .collect(Collectors.toList());
      return ResponseEntity.ok(responseDTOs); 
    }
    
   
    // utility methods for mapping
    private User mapToEntity(@Valid UserRegistrationDTO registrationDTO) {
        // map UserRegistrationDTO to User entity
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail((registrationDTO.getEmail()));
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setPassword(registrationDTO.getPassword());
        user.setIsBusiness(registrationDTO.getIsBusiness());
        user.setCreatedAt(registrationDTO.getCreatedAt());
        user.setUpdatedAt(registrationDTO.getUpdatedAt());

        return user;
    }

    private UserResponseDTO mapToDTO(User user) {
        // map user entity to UserResponstDTO
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserId(user.getUserId());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setFirstName(user.getFirstName());
        responseDTO.setLastName(user.getLastName());
        responseDTO.setFullName(user.getFirstName() + " " + user.getLastName());
        responseDTO.setIsBusiness(user.getIsBusiness());

        return responseDTO;
    }
}
