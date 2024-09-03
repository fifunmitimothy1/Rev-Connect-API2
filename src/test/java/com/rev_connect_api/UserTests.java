package com.rev_connect_api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import com.rev_connect_api.models.User;

public class UserTests {

    private User underTest = new User(
            "testuser",
            "password123",
            "testuser@gmail.com",
            "Test",
            "User",
            true);

    @Test
    public void testUserCreation() {
        User testUser = new User(
                "flamehashira",
                "brightredblade",
                "rengoku@demonslayercorp.net",
                "Kyojuro",
                "Rengoku",
                false);

        assertThat(testUser).isNotNull();
        assertThat(testUser).isNotEqualTo(underTest);
        //assertThat(testUser.getUserName()).isEqualTo("flamehashira");
        assertThat(testUser.getFirstName()).isEqualTo("Kyojuro");
        assertThat(testUser.getLastName()).isEqualTo("Rengoku");
        //assertThat(testUser.getUserEmail()).isEqualTo("rengoku@demonslayercorp.net");
        assertThat(testUser.getPassword()).isEqualTo("brightredblade");
        //assertThat(testUser.getBusiness()).isFalse();
    }

    @Test
    public void testSettersAndGetters() {
        User testUser = new User();
        //testUser.setId(5L);
        //testUser.setUserName("flamehashira");
        testUser.setFirstName("Kyojuro");
        testUser.setLastName("Rengoku");
        //testUser.setUserEmail("rengoku@demonslayercorp.net");
        testUser.setPassword("brightredblade");
        //testUser.setBusiness(false);

        assertThat(testUser).isNotNull();
        assertThat(testUser).isNotEqualTo(underTest);
        //assertThat(testUser.getId()).isEqualTo(5L);
        //assertThat(testUser.getUserName()).isEqualTo("flamehashira");
        assertThat(testUser.getFirstName()).isEqualTo("Kyojuro");
        assertThat(testUser.getLastName()).isEqualTo("Rengoku");
        //assertThat(testUser.getUserEmail()).isEqualTo("rengoku@demonslayercorp.net");
        assertThat(testUser.getPassword()).isEqualTo("brightredblade");
        //assertThat(testUser.getBusiness()).isFalse();
    }
}