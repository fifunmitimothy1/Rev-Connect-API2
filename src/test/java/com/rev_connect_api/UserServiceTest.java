package com.rev_connect_api.services;

import com.rev_connect_api.dto.UserSearchResult;
import com.rev_connect_api.entity.UserConnection;
import com.rev_connect_api.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConnectionRequestService connectionRequestService;

    @InjectMocks
    private UserService userService;

    private UserConnection user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserConnection();
        user.setAccountId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
    }

    @Test
    void testSearchUsersWithConditions() {
        when(userRepository.searchUsersByUsernameStartingWith("test")).thenReturn(List.of(user));
        when(connectionRequestService.hasPendingRequest(1L, 1L)).thenReturn(false);

        List<UserSearchResult> results = userService.searchUsersWithConditions("test", 1L);

        assertEquals(1, results.size());
        assertEquals("testuser", results.get(0).getUsername());
    }
}

