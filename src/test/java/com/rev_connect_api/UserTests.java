package com.rev_connect_api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import com.rev_connect_api.models.User;

public class UserTests {

    private User underTest = new User(
            1L,
            "codybonham",
            "Cody",
            "Bonham",
            "cody187@revature.net",
            "password123",
            true);

    @Test
    public void testUserCreation() {
        User testUser = new User(
                2L,
                "flamehashira",
                "Kyojuro",
                "Rengoku",
                "rengoku@demonslayercorp.net",
                "brightredblade",
                false);

        assertThat(testUser).isNotNull();
        assertThat(testUser).isNotEqualTo(underTest);
        assertThat(testUser.getId()).isEqualTo(2L);
        assertThat(testUser.getUserId()).isEqualTo("flamehashira");
        assertThat(testUser.getFirstName()).isEqualTo("Kyojuro");
        assertThat(testUser.getLastName()).isEqualTo("Rengoku");
        assertThat(testUser.getUserEmail()).isEqualTo("rengoku@demonslayercorp.net");
        assertThat(testUser.getPassword()).isEqualTo("brightredblade");
        assertThat(testUser.getBusiness()).isFalse();

    }
}