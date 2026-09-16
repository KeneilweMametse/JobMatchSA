package za.co.jobmatchsa.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileServiceTest {

    private final ProfileService profileService = new ProfileService();

    @Test
    void validInputPassesValidation() {
        String result = profileService.validateInput("Cape Town", 3, "Java, SQL", false, null);
        assertNull(result);
    }

    @Test
    void blankLocationIsRejected() {
        String result = profileService.validateInput("", 3, "Java", false, null);
        assertEquals("Location cannot be empty.", result);
    }

    @Test
    void nullLocationIsRejected() {
        String result = profileService.validateInput(null, 3, "Java", false, null);
        assertEquals("Location cannot be empty.", result);
    }

    @Test
    void negativeExperienceIsRejected() {
        String result = profileService.validateInput("Cape Town", -1, "Java", false, null);
        assertEquals("Years of experience cannot be negative.", result);
    }

    @Test
    void zeroExperienceIsAllowed() {
        String result = profileService.validateInput("Cape Town", 0, "Java", false, null);
        assertNull(result);
    }

    @Test
    void blankSkillsAreRejected() {
        String result = profileService.validateInput("Cape Town", 2, "  ", false, null);
        assertEquals("Please list at least one skill.", result);
    }

    @Test
    void smsWithoutPhoneNumberIsRejected() {
        String result = profileService.validateInput("Cape Town", 2, "Java", true, null);
        assertEquals("Phone number is required for SMS notifications.", result);
    }

    @Test
    void smsWithBlankPhoneNumberIsRejected() {
        String result = profileService.validateInput("Cape Town", 2, "Java", true, "  ");
        assertEquals("Phone number is required for SMS notifications.", result);
    }

    @Test
    void smsWithPhoneNumberPasses() {
        String result = profileService.validateInput("Cape Town", 2, "Java", true, "0821234567");
        assertNull(result);
    }

    @Test
    void phoneNumberNotRequiredWhenSmsNotSelected() {
        String result = profileService.validateInput("Cape Town", 2, "Java", false, null);
        assertNull(result);
    }
}