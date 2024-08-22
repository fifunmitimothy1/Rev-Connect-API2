package com.rev_connect_api.controllers;

import com.rev_connect_api.entity.User;
import com.rev_connect_api.models.UserDTO;
import com.rev_connect_api.services.UserService;
import com.rev_connect_api.services.EmailService;
import com.rev_connect_api.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/home")
public class HomeController {
    //
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;


    // http://127.0.0.1:8080/home/login
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        try {

            //loginAccount check from database
            User loginAccount = userService.login(user);
            loginAccount.setPassword(null);

            // username
            String username = loginAccount.getUsername();
            Integer accountId = loginAccount.getAccountId();
            // generate token
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", username);
            claims.put("accountId", accountId);
            String token = JwtUtil.generateToken(claims);

            //DTO send to frond end
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(loginAccount.getUsername());
            userDTO.setEmail(loginAccount.getEmail());
            userDTO.setAccountId(loginAccount.getAccountId());
            userDTO.setToken(token);

            //send to front
            return new ResponseEntity<>(userDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }



    @GetMapping("/getUserInfo")
    public ResponseEntity<Object> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>("请登录后再使用", HttpStatus.UNAUTHORIZED);
        }

        UserDTO userDTO = new UserDTO();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    // Endpoint to handle password reset requests
    @PostMapping("/forgotPassword")
    public ResponseEntity<Object> forgotPassword(@RequestBody Map<String, String> request) {
        // Get the email address from the request body
        String email = request.get("email");
        String response = userService.forgotPass(email);
        if ("Invalid email.".equals(response)) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Endpoint to handle password changes using a reset token
    @PutMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestParam String token, @RequestBody Map<String, String> passwords) {
        // Get the new password from the request body
        String newPassword = passwords.get("newPassword");
        // Call the service to reset the password using the token
        String response = userService.resetPass(token, newPassword);
        if ("Invalid token.".equals(response)) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}




