package com.rev_connect_api;

import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.models.User;

/**
 * Basic test data used for tests
 */
public final class BusinessProfileTestDataUtil {
    public static User createTestUser1() {
        return new User((long) 111, "test1", "pw1", "joe1", "doe1", "test1@email", true);
    }
    public static User createTestUser2() {
        return new User((long) 112, "test2", "pw2", "joe2", "doe2", "test2@email", true);
    }
    public static User createTestUser3() {
        return new User((long) 113, "test3", "pw3", "joe3", "doe3", "test3@email", false);
    }
    public static User createTestUser4() {
        return new User((long) 114, "test4", "pw4", "joe4", "doe4", "test4@email", false);
    }

    public static BusinessProfile createTestProfileA() {
        return new BusinessProfile(999, "Test Bio 1", createTestUser1());
    }

    public static BusinessProfile createTestProfileB() {
        return new BusinessProfile(998,"Test Bio 2", createTestUser2());
    }

    public static BusinessProfile createTestProfileC() {
        return new BusinessProfile(997, "Test Bio 3", createTestUser3());
    }

    public static BusinessProfile createTestProfileD() {
        return new BusinessProfile(996, "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678900", createTestUser4());
    }

    private BusinessProfileTestDataUtil() {}

}
