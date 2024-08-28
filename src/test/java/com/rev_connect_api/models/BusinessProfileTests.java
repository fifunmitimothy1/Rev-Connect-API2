package com.rev_connect_api.models;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.rev_connect_api.BusinessProfileTestDataUtil;

public class BusinessProfileTests {

    private BusinessProfile noArgsTest = new BusinessProfile();
    private BusinessProfile noIDArgTest = new BusinessProfile(BusinessProfileTestDataUtil.createTestUser1(), "Test Bio 1",  "white", "testPfpURL1.com", "testBannerURL1.com");
    private BusinessProfile allArgTest = new BusinessProfile(998, BusinessProfileTestDataUtil.createTestUser2(), "Test Bio 2", "red", "testPfpURL2.com", "testBannerURL2.com");

    @Test
    void noArgsConstructorTestCreatesProfile() {
        assertThat(noArgsTest).isNotNull();
    }

    @Test
    void noIdArgConstructorTestCreatesProfile() {
        assertThat(noIDArgTest).isNotNull();
    }

    @Test
    void AllArgConstructorTestCreatesProfile() {
        assertThat(allArgTest).isNotNull();
    }

    @Test
    void newProfilesAreCreated() {
        BusinessProfile allArgTestTwo = new BusinessProfile(998, BusinessProfileTestDataUtil.createTestUser2(), "Test Bio 2", "red", "testPfpURL2.com", "testBannerURL2.com");
        assertThat(allArgTestTwo).isNotNull();
        assertThat(allArgTestTwo).isNotEqualTo(allArgTest);
    }

    @Test
    void gettersGetCorrectValue() {
        assertEquals(allArgTest.getId(), 998);
        assertEquals(allArgTest.getBioText(), "Test Bio 2");
        assertThat(allArgTest.getUser()).hasToString(BusinessProfileTestDataUtil.createTestUser2().toString());
        assertEquals(allArgTest.getTheme(), "red");
        assertEquals(allArgTest.getProfilePictureURL(), "testPfpURL2.com");
        assertEquals(allArgTest.getBannerURL(), "testBannerURL2.com");
    }

    @Test
    void settersSetValue() {
        BusinessProfile blank = new BusinessProfile();
        blank.setId(5555);
        blank.setUser(BusinessProfileTestDataUtil.createTestUser1());
        blank.setBioText("Setters are working!");
        blank.setTheme("purple");
        blank.setProfilePictureURL("testPfp.com");
        blank.setBannerURL("testBanner.com");

        assertThat(blank.getId()).isNotNull().isEqualTo(5555);
        assertThat(blank.getUser()).isNotNull().hasToString(BusinessProfileTestDataUtil.createTestUser1().toString());
        assertThat(blank.getBioText()).isNotBlank().isEqualTo("Setters are working!");
        assertThat(blank.getTheme()).isNotBlank().isEqualTo("purple");
        assertThat(blank.getProfilePictureURL()).isNotBlank().isEqualTo("testPfp.com");
        assertThat(blank.getBannerURL()).isNotBlank().isEqualTo("testBanner.com");
    }

}