package com.rev_connect_api.mapper;

import com.rev_connect_api.dto.UserRegistrationDTO;
import com.rev_connect_api.dto.UserResponseDTO;
import com.rev_connect_api.dto.UserUpdateDTO;
import com.rev_connect_api.models.Role;
import com.rev_connect_api.models.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testToEntity() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setUsername("testuser");
        dto.setPassword("testpass");
        dto.setEmail("test@example.com");
        dto.setFirstName("Test");
        dto.setLastName("User");
        dto.setIsBusiness(false);
        dto.setRoles(Set.of("ROLE_USER"));

        User user = userMapper.toEntity(dto);

        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("testpass", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals(Set.of(Role.ROLE_USER), user.getRoles());
    }

    @Test
    public void testToDTO() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setIsBusiness(false);
        user.setRoles(Set.of(Role.ROLE_USER));

        UserResponseDTO dto = userMapper.toDTO(user);

        assertNotNull(dto);
        assertEquals(1L, dto.getUserId());
        assertEquals("testuser", dto.getUsername());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("Test User", dto.getFullName());
        assertEquals(Set.of(Role.ROLE_USER), dto.getRoles());
    }

    @Test
    public void testUpdateUserFromDTO() {
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setUsername("updateduser");
        dto.setEmail("updated@example.com");
        dto.setFirstName("Updated");
        dto.setLastName("User");
        dto.setIsBusiness(true);
        dto.setRoles(Set.of("ROLE_ADMIN"));

        User user = new User();
        user.setUsername("originaluser");
        user.setEmail("original@example.com");
        user.setFirstName("Original");
        user.setLastName("User");
        user.setIsBusiness(false);
        user.setRoles(Set.of(Role.ROLE_USER));

        userMapper.updateUserFromDTO(dto, user);

        assertEquals("updateduser", user.getUsername());
        assertEquals("updated@example.com", user.getEmail());
        assertEquals("Updated", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals(true, user.getIsBusiness());
        assertEquals(Set.of(Role.ROLE_ADMIN), user.getRoles());
    }
}