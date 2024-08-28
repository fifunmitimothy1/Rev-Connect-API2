package com.rev_connect_api;

import com.rev_connect_api.dto.UserRegistrationDTO;
import com.rev_connect_api.dto.UserUpdateDTO;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void registerUser() throws Exception {
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setUsername("newuser");
        registrationDTO.setPassword("password");
        registrationDTO.setEmail("newuser@example.com");
        registrationDTO.setFirstName("New");
        registrationDTO.setLastName("User");
        registrationDTO.setIsBusiness(false);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("newuser"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserById() throws Exception {
        User user = new User(
                "testuser",
                "password",
                "testuser@example.com",
                "Test",
                "User",
                false);
        user.setRoles(Set.of(Role.ROLE_USER));
        user = userRepository.save(user);

        mockMvc.perform(get("/users/{id}", user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testuser"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateUser() throws Exception {
        User user = new User(
                "testuser",
                "password",
                "testuser@example.com",
                "Test",
                "User",
                false);
        user.setRoles(Set.of(Role.ROLE_USER));
        user = userRepository.save(user);

        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setEmail("updateduser@example.com");

        mockMvc.perform(put("/users/{id}", user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("updateduser@example.com"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteUser() throws Exception {
        User user = new User(
                "testuser",
                "password",
                "testuser@example.com",
                "Test",
                "User",
                false);
        user.setRoles(Set.of(Role.ROLE_USER));
        user = userRepository.save(user);

        mockMvc.perform(delete("/users/{id}", user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getAllUsers() throws Exception {
        User user1 = new User(
                "testuser1",
                "password",
                "testuser1@example.com",
                "Test1",
                "User1",
                false);
        user1.setRoles(Set.of(Role.ROLE_USER));
        userRepository.save(user1);

        User user2 = new User(
                "testuser2",
                "password",
                "testuser2@example.com",
                "Test2",
                "User2",
                false);
        user2.setRoles(Set.of(Role.ROLE_USER));
        userRepository.save(user2);

//        mockMvc.perform(get("/users/")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }
}
