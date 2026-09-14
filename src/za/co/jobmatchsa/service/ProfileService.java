package za.co.jobmatchsa.service;

import za.co.jobmatchsa.dao.CandidateProfileDAO;
import za.co.jobmatchsa.model.CandidateProfile;

/**
 * ProfileService handles the business logic for creating and validating
 * candidate profiles — including the auto-apply and notification preferences
 * that drive how Job Match SA behaves for each user.
 */
public class ProfileService {

    private final CandidateProfileDAO profileDAO = new CandidateProfileDAO();

    /**
     * Creates a candidate profile for a given user.
     * Returns a message indicating success or the reason for failure.
     */
    public String createProfile(int userId, String location, int yearsExperience,
                                String educationLevel, String skills,
                                boolean autoApply, boolean notifyEmail,
                                boolean notifySms, boolean notifyJobLinks,
                                String phoneNumber) {
        
    // Run cheap, pure validation before touching the database
        String validationError = validateInput(location, yearsExperience, skills, notifySms, phoneNumber);
        if (validationError != null) {
            return validationError;
        }

    // Prevent duplicate profiles
        if (profileDAO.profileExists(userId)) {
            return "A profile already exists for this user.";
        }


        CandidateProfile profile = new CandidateProfile(
                userId, location, yearsExperience, educationLevel, skills,
                autoApply, notifyEmail, notifySms, notifyJobLinks, phoneNumber
        );

        boolean success = profileDAO.createProfile(profile);

        if (success) {
            return "Profile created successfully!";
        } else {
            return "Failed to create profile. Please try again.";
        }
    }

    /**
     * Validates profile input with no database access, so it can be unit
     * tested directly. Returns an error message, or null if input is valid.
     */
    public String validateInput(String location, int yearsExperience, String skills,
                                 boolean notifySms, String phoneNumber) {
        if (location == null || location.isBlank()) {
            return "Location cannot be empty.";
        }
        if (yearsExperience < 0) {
            return "Years of experience cannot be negative.";
        }
        if (skills == null || skills.isBlank()) {
            return "Please list at least one skill.";
        }
        if (notifySms && (phoneNumber == null || phoneNumber.isBlank())) {
            return "Phone number is required for SMS notifications.";
        }
        return null;
    }

    /**
     * Retrieves a user's candidate profile.
     */
    public CandidateProfile getProfile(int userId) {
        return profileDAO.findByUserId(userId);
    }
}
