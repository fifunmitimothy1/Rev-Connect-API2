package com.rev_connect_api;

import com.rev_connect_api.models.User;
import com.rev_connect_api.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testRegisterUserFailure() throws Exception {
        Mockito.doNothing().when(userService).register(Mockito.any(User.class));
        Mockito.when(userService.checkUserId("janesmith")).thenReturn(null);

        mockMvc.perform(post("/register")
                        .param("firstName", "Jane")
                        .param("lastName", "Smith")
                        .param("userName", "janesmith")
                        .param("email", "jane.smith@example.com")
                        .param("password", "password123")
                        .param("isBusiness", "false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("User not found!")));
    }
}

