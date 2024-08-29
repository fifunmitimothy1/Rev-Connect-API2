package com.rev_connect_api.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rev_connect_api.dto.UserUpdateDTO;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // find a user by username
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    // find users by username or password
    public List<User> getUserDetails(String userName,String email){
         return userRepository.findByUsernameOrEmail(userName, email);
    }

    public User registerUser(User user){
        String username = user.getUsername();
        String emailId = user.getEmail();
        List<User> checkDuplicates=getUserDetails(username,emailId);

        if(checkDuplicates.stream().anyMatch(userDetails->emailId.equals(userDetails.getEmail())))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");

        if(checkDuplicates.stream().anyMatch(userDetails->username.equals(userDetails.getUsername())))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");

        if(user.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.ROLE_USER));
        }

        // hashing password then persisting hashed password to the database
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.saveAndFlush(user);
    }

    public User updateUser(Long id, UserUpdateDTO updateDTO) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (updateDTO.getUsername() != null) user.setUsername(updateDTO.getUsername());
        if (updateDTO.getEmail() != null) user.setEmail(updateDTO.getEmail());
        if (updateDTO.getFirstName() != null) user.setFirstName(updateDTO.getFirstName());
        if (updateDTO.getLastName() != null) user.setLastName(updateDTO.getLastName());
        if (updateDTO.getIsBusiness() != null) user.setIsBusiness(updateDTO.getIsBusiness());
        if (updateDTO.getPassword() != null) {
            // hash the new password before update
            String hashedPassword = passwordEncoder.encode(updateDTO.getPassword());
            user.setPassword(hashedPassword);
        }
        if(updateDTO.getRoles() != null) user.setRoles(mapRoles(updateDTO.getRoles()));

        return userRepository.saveAndFlush(user);
    }
    
    private Set<Role> mapRoles(Set<String> roles) {
        // same mapping logic as in UserMapper interface
        return roles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(BigInteger id) {
       return userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
