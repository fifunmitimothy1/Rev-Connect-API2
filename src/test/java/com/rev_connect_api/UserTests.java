package com.rev_connect_api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.models.User;

public class UserTests {


    private User underTest = new User(
            "codybonham",
            "Cody",
            "Bonham",
            "cody187@revature.net",
            "password123",
            true);

    @Test
    public void testUserCreation() {
        User testUser = new User(
                "flamehashira",
                "Kyojuro",
                "Rengoku",
                "rengoku@demonslayercorp.net",
                "brightredblade",
                false);

        assertThat(testUser).isNotNull();
        assertThat(testUser).isNotEqualTo(underTest);
        assertThat(testUser.getUsername()).isEqualTo("flamehashira");
        assertThat(testUser.getFirstName()).isEqualTo("Kyojuro");
        assertThat(testUser.getLastName()).isEqualTo("Rengoku");
        assertThat(testUser.getUserEmail()).isEqualTo("rengoku@demonslayercorp.net");
        assertThat(testUser.getPassword()).isEqualTo("brightredblade");
        assertThat(testUser.getBusiness()).isFalse();
    }

    @Test
    public void testSettersAndGetters() {
        User testUser = new User();
        testUser.setUsername("flamehashira");
        testUser.setFirstName("Kyojuro");
        testUser.setLastName("Rengoku");
        testUser.setUserEmail("rengoku@demonslayercorp.net");
        testUser.setPassword("brightredblade");
        testUser.setBusiness(false);

        assertThat(testUser).isNotNull();
        assertThat(testUser).isNotEqualTo(underTest);
        assertThat(testUser.getUsername()).isEqualTo("flamehashira");
        assertThat(testUser.getFirstName()).isEqualTo("Kyojuro");
        assertThat(testUser.getLastName()).isEqualTo("Rengoku");
        assertThat(testUser.getUserEmail()).isEqualTo("rengoku@demonslayercorp.net");
        assertThat(testUser.getPassword()).isEqualTo("brightredblade");
        assertThat(testUser.getBusiness()).isFalse();
    }
}