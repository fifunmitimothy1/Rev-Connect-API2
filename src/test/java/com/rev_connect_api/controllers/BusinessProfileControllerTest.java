// package com.rev_connect_api.controllers;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotEquals;
// import static org.mockito.Mockito.*;

// import java.util.*;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import com.rev_connect_api.BusinessProfileTestDataUtil;
// import com.rev_connect_api.models.BusinessProfile;
// import com.rev_connect_api.models.User;
// import com.rev_connect_api.services.BusinessProfileService;

// /**
//  * Business Profile Controller Tests
//  */
// @ExtendWith(MockitoExtension.class)
// public class BusinessProfileControllerTest {
    
//     @Mock private BusinessProfileService businessProfileService;
//     @InjectMocks public BusinessProfileController underTest;
    
//     /**
//      * Test to ensure HTTP is 200 when returning list of profiles
//      */
//     @Test
//     public void getAllBusinessProfilesReturns200() {
//         final List<BusinessProfile> profiles = List.of(
//             BusinessProfileTestDataUtil.createTestProfileA(),
//             BusinessProfileTestDataUtil.createTestProfileB()
//         );
//         when(businessProfileService.findAllBusinessProfiles()).thenReturn(profiles);
//         final ResponseEntity<List<BusinessProfile>> result = underTest.getBusinessProfiles();
//         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//     }

//     /**
//      * Test to ensure HTTP is 200 when returning empty list of profiles
//      */
//     @Test
//     public void getAllBusinessProfilesEmptyReturns200() {
//         final List<BusinessProfile> profiles = List.of();
//         when(businessProfileService.findAllBusinessProfiles()).thenReturn(profiles);
//         final ResponseEntity<List<BusinessProfile>> result = underTest.getBusinessProfiles();
//         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//     }

//     /**
//      * Test to ensure HTTP is 200 when returning profile by id
//      */
//     @Test
//     public void getBusinessProfileByUserIdReturns200() {
//         final BusinessProfile testProfile = BusinessProfileTestDataUtil.createTestProfileA();
//         when(businessProfileService.findByUserId(111)).thenReturn(testProfile);
//         final ResponseEntity<BusinessProfile> result = underTest.getBusinessProfileByUserId(111);
//         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//     }

//     /**
//      * Test to ensure HTTP is 404 when profile for a user id doesn't exist
//      */
//     @Test
//     public void getBusinessProfileByUserIdUserNotFoundReturns404() {
//         final ResponseEntity<BusinessProfile> result = underTest.getBusinessProfileByUserId(10);
//         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//     }

//     /**
//      * Test to ensure HTTP is 200 when profile is updated successfully
//      */
//     @Test
//     public void updateBusinessProfileSuccessReturns200() {
//         final long userId = 111;
//         final BusinessProfile oldBioProfile = BusinessProfileTestDataUtil.createTestProfileA();
//         final BusinessProfile updatedProfile = new BusinessProfile(999, BusinessProfileTestDataUtil.createTestUser1(), "Updated Bio", "Gold", "newPfpURL.com", "newBannerURL.com");
//         when(businessProfileService.updateProfile(updatedProfile, userId))
//             .thenReturn(updatedProfile);
//         final ResponseEntity<BusinessProfile> result = underTest.updateBusinessProfile(updatedProfile, userId);
//         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//     }

//     /**
//      * Test to ensure HTTP is 409 when updating bio is unsuccessful
//      */
//     @Test
//     public void updateBioTextUnSuccessfulReturns409() {
//         final BusinessProfile oldBioProfile = BusinessProfileTestDataUtil.createTestProfileA();
//         final BusinessProfile updatedProfile = new BusinessProfile(999, BusinessProfileTestDataUtil.createTestUser1(), "Updated Bio", "Gold", "newPfpURLom", "newBannerURL.com");
//         when(businessProfileService.updateProfile(updatedProfile, 10))
//             .thenReturn(null);
//         final ResponseEntity<BusinessProfile> result = underTest.updateBusinessProfile(updatedProfile, 10);
//         assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
//     }

// }
