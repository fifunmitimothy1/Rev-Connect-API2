package com.rev_connect_api.repositories;

import com.rev_connect_api.entity.UserConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserConnection user1;
    private UserConnection user2;

    @BeforeEach
    void setUp() {
        user1 = new UserConnection();
        user1.setUsername("john_doe");
        user1.setEmail("john@example.com");
        userRepository.save(user1);

        user2 = new UserConnection();
        user2.setUsername("jane_doe");
        user2.setEmail("jane@example.com");
        userRepository.save(user2);
    }

    @Test
    void testFindByUsernameOrEmail() {
        Optional<UserConnection> foundUser = userRepository.findByUsernameOrEmail("john_doe", "john@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals("john_doe", foundUser.get().getUsername());
    }

    @Test
    void testSearchUsersByUsernameStartingWith() {
        List<UserConnection> users = userRepository.searchUsersByUsernameStartingWith("john");
        assertEquals(1, users.size());
        assertEquals("john_doe", users.get(0).getUsername());
    }
}
