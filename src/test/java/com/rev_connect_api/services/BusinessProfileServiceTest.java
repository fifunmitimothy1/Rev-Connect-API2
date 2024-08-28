package com.rev_connect_api.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.rev_connect_api.BusinessProfileTestDataUtil;
import com.rev_connect_api.exceptions.BioTextTooLongException;
import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.repositories.BusinessProfileRepository;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class BusinessProfileServiceTest {

    @Mock private BusinessProfileRepository businessProfileRepository;
    @InjectMocks private BusinessProfileService underTest;
    
    /**
     * Test for successfully finding a profile by user id
     */
    @Test
    public void findByUserIdFound() {
        final BusinessProfile expected = BusinessProfileTestDataUtil.createTestProfileA();

        when(businessProfileRepository.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUser().getId()))
            .thenReturn(BusinessProfileTestDataUtil.createTestProfileA());

        final BusinessProfile result = underTest.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUser().getId());
        assertThat(result).isNotNull().hasToString(expected.toString());
    }

    /**
     * Test for profile not existing for a user id
     */
    @Test
    public void findByUserIdNotFound() {
        final BusinessProfile expected = null;

        when(businessProfileRepository.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUser().getId()))
            .thenReturn(null);

        final BusinessProfile result = underTest.findByUserId(BusinessProfileTestDataUtil.createTestProfileA().getUser().getId());
        assertThat(result).isEqualTo(expected);
    }

    /**
     * Test that getting all profiles successfully returns a list
     */
    @Test
    public void findAllBusinessProfilesReturnsAListWhenPresent() {
        final List<BusinessProfile> profileList = 
            List.of(
                BusinessProfileTestDataUtil.createTestProfileA(),
                BusinessProfileTestDataUtil.createTestProfileB(),
                BusinessProfileTestDataUtil.createTestProfileC()
            );
        when(businessProfileRepository.findAll()).thenReturn(profileList);
        assertThat(underTest.findAllBusinessProfiles())
            .isNotEmpty();
    }

    /**
     * Test that getting all profiles when there are none returns an empty list
     */
    @Test
    public void findAllBusinessProfilesReturnsEmptyListIfNone() {
        final List<BusinessProfile> profileList = 
            List.of();
        when(businessProfileRepository.findAll()).thenReturn(profileList);
        assertThat(underTest.findAllBusinessProfiles())
            .isNotNull()
            .isEmpty();
    }

    /**
     * Testing successful updating of bio text of a business profile
     */
    @Test
    public void testUpdateBioText_Success() {
        long userId = 111;
        
        BusinessProfile existingProfile = BusinessProfileTestDataUtil.createTestProfileA();
        existingProfile.setBioText("Old bio text");
    
        BusinessProfile updatedBusinessProfile = new BusinessProfile();
        updatedBusinessProfile.setUser(BusinessProfileTestDataUtil.createTestUser1());
        updatedBusinessProfile.setBioText("Updated bioText");
        updatedBusinessProfile.setTheme("Magenta");
        updatedBusinessProfile.setBannerURL("updatedBanner.com");
        updatedBusinessProfile.setProfilePictureURL("updatedPfp.com");

        when(businessProfileRepository.findByUserId(userId)).thenReturn(existingProfile);

        BusinessProfile result = underTest.updateProfile(updatedBusinessProfile, userId);

        assertNotNull(result);
        assertEquals(result.getUser().getId(), 111);
        assertEquals(result.getBioText(), "Updated bioText");
        assertEquals(result.getTheme(), "Magenta");
        assertEquals(result.getBannerURL(), "updatedBanner.com");
        assertEquals(result.getProfilePictureURL(), "updatedPfp.com");
    }

    /**
     * Testing error is thrown when bio text to update is too long
     */
    @Test
    public void testUpdateBioText_BioTextTooLong() {
        long userId = 1L;
        String longBioText = "A".repeat(501);
        BusinessProfile businessProfile = BusinessProfileTestDataUtil.createTestProfileA();
        businessProfile.setBioText(longBioText);

        BioTextTooLongException thrown = assertThrows(BioTextTooLongException.class, () -> {
            underTest.updateProfile(businessProfile, userId);
        });

        assertEquals("Exceeding 500 character limit", thrown.getMessage());
    }
    
    /**
     * Testing getting all profile information for a given user successfully
     */
    @Test
    public void findFullProfileByUserIdFound() {
        final BusinessProfile findProfile = BusinessProfileTestDataUtil.createTestProfileB();
        when(businessProfileRepository.findByUserId(112))
            .thenReturn(findProfile);
        final BusinessProfile result = underTest.findByUserId(112);
        assertThat(result).isNotNull();
    }

    /**
     * Testing if no profile information can be found, returning null
     */
    @Test
    public void findFullProfileByUserIdNotFound() {
        when(businessProfileRepository.findByUserId(10))
        .thenReturn(null);
        final BusinessProfile result = underTest.findByUserId(10);
        assertThat(result).isNull();
    }


}
