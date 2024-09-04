package com.rev_connect_api.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.Profiles;
import org.springframework.data.util.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rev_connect_api.dto.UserUpdateDTO;
import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.models.PersonalProfile;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRE_TOKEN = 15; // Expiration time in minutes
    private final ProfileService profileService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService, ProfileService profileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.profileService = profileService;
    }

    // Find a user by username
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    // Find users by username or email
    public List<User> getUserDetails(String userName, String email) {
        return userRepository.findByUsernameOrEmail(userName, email);
    }

    // Find user by id
    public User getUserById(Long userId) throws InvalidUserException{
        Optional<User> oUser = userRepository.findById(userId);
        if (oUser.isPresent()) {
            return oUser.get();
        }
        else {
            throw new InvalidUserException("Cannot find user for id: " + userId);
        }
    }

    // Register a new user
    public User registerUser(User user) {
        String username = user.getUsername();
        String emailId = user.getEmail();
        List<User> checkDuplicates = getUserDetails(username, emailId);

        if (checkDuplicates.stream().anyMatch(userDetails -> emailId.equals(userDetails.getEmail())))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");

        if (checkDuplicates.stream().anyMatch(userDetails -> username.equals(userDetails.getUsername())))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");

        if (user.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.ROLE_USER));
        }

        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        user.setProfile(profileService.createProfile(user.getIsBusiness()));

        return userRepository.saveAndFlush(user);
    }

    // Update an existing user
    public User updateUser(Long id, UserUpdateDTO updateDTO) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (updateDTO.getUsername() != null) user.setUsername(updateDTO.getUsername());
        if (updateDTO.getEmail() != null) user.setEmail(updateDTO.getEmail());
        if (updateDTO.getFirstName() != null) user.setFirstName(updateDTO.getFirstName());
        if (updateDTO.getLastName() != null) user.setLastName(updateDTO.getLastName());
        if (updateDTO.getIsBusiness() != null) user.setIsBusiness(updateDTO.getIsBusiness());
        if (updateDTO.getPassword() != null) {
            // Hash the new password before updating
            String hashedPassword = passwordEncoder.encode(updateDTO.getPassword());
            user.setPassword(hashedPassword);
        }
        if (updateDTO.getRoles() != null) user.setRoles(mapRoles(updateDTO.getRoles()));
        if (updateDTO.getProfile() != null) user.setProfile(updateDTO.getProfile());

        return userRepository.saveAndFlush(user);
    }

    // Map roles from String to Role
    private Set<Role> mapRoles(Set<String> roles) {
        return roles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Find a user by ID
    public User findUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    // Generate a JWT token for password reset
    // @SuppressWarnings("deprecation")
    public String generateResetToken(String email) {
        long expirationTime = EXPIRE_TOKEN * 60 * 1000; // 15 minutes expiration

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(secretKey)
            .compact();
    }

    // Handle forgot password request
    public String forgotPass(String email) {
        // Find the user by email
        Optional<User> userOptional = Optional.empty();

        if (userOptional.isEmpty()) {
            return "Invalid email id.";
        }
        // Generate reset token
        String resetToken = generateResetToken(email);
        // Construct the frontend reset link including the token
        String resetLink = "http://localhost:5173/reset-password?token=" + resetToken;

        // Send reset link to the provided email address
        emailService.sendEmail(email, "Password Reset Request", "Click the link to reset your password: " + resetLink);

        return resetLink;
    }

    // Handle password reset request
    public String resetPass(String token, String newPassword) {
        try {
            Claims claims = Jwts.parser()
                // .setSigningKey(secretKey)
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
            String email = claims.getSubject();
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                return "Invalid token.";
            }
            User user = userOptional.get();
            // Hash the new password before saving
            String hashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(hashedPassword);
            userRepository.save(user);
            return "Your password has been successfully updated.";
        } catch (JwtException e) {
            return "Invalid or expired token.";
        }
    }

}
