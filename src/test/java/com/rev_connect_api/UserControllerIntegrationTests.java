package com.rev_connect_api;

import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;
import com.rev_connect_api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testRegisterUserSuccess() throws Exception {
        mockMvc.perform(post("/register")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("userName", "johndoe")
                        .param("email", "john.doe@example.com")
                        .param("password", "password123")
                        .param("isBusiness", "false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.user.userName", is("johndoe")));
    }

//    @Test
//    void testRegisterUserAlreadyExists() throws Exception {
//        User existingUser = new User("johndoe", "John", "Doe", "john.doe@example.com", "password123", false);
//        userRepository.save(existingUser);
//
//        mockMvc.perform(post("/register")
//                        .param("firstName", "Jane")
//                        .param("lastName", "Doe")
//                        .param("userName", "johndoe")
//                        .param("email", "jane.doe@example.com")
//                        .param("password", "password456")
//                        .param("isBusiness", "false")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(jsonPath("$.success", is(false)))
//                .andExpect(jsonPath("$.message", is("User not found!")));
//    }

    @Test
    void testCheckUserIdExists() throws Exception {
        User existingUser = new User("johndoe", "John", "Doe", "john.doe@example.com", "password123", false);
        userRepository.save(existingUser);

        mockMvc.perform(post("/checkUserId")
                        .param("userName", "johndoe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    void testCheckUserIdNotExists() throws Exception {
        mockMvc.perform(post("/checkUserId")
                        .param("userName", "nonexistentuser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }
}