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

        // Prevent duplicate profiles
        if (profileDAO.profileExists(userId)) {
            return "A profile already exists for this user.";
        }

        // Basic validation
        if (location == null || location.isBlank()) {
            return "Location cannot be empty.";
        }
        if (yearsExperience < 0) {
            return "Years of experience cannot be negative.";
        }
        if (skills == null || skills.isBlank()) {
            return "Please list at least one skill.";
        }

        // Phone number required if SMS notifications selected
        if (notifySms && (phoneNumber == null || phoneNumber.isBlank())) {
            return "Phone number is required for SMS notifications.";
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
     * Retrieves a user's candidate profile.
     */
    public CandidateProfile getProfile(int userId) {
        return profileDAO.findByUserId(userId);
    }
}