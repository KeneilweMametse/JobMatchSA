package za.co.jobmatchsa.model;

import java.time.LocalDateTime;

/**
 * CandidateProfile stores a user's professional details and preferences.
 * Maps to the 'candidate_profiles' table. One profile per user.
 */
public class CandidateProfile {

    private int id;
    private int userId;
    private String location;
    private int yearsExperience;
    private String educationLevel;
    private String skills;             // comma-separated e.g. "Java,SQL,Python"
    private boolean autoApply;
    private boolean notifyEmail;
    private boolean notifySms;
    private boolean notifyJobLinks;
    private String phoneNumber;
    private LocalDateTime updatedAt;

    // Default constructor
    public CandidateProfile() {}

    // Constructor for creating a new profile
    public CandidateProfile(int userId, String location, int yearsExperience,
                            String educationLevel, String skills,
                            boolean autoApply, boolean notifyEmail,
                            boolean notifySms, boolean notifyJobLinks,
                            String phoneNumber) {
        this.userId          = userId;
        this.location        = location;
        this.yearsExperience = yearsExperience;
        this.educationLevel  = educationLevel;
        this.skills          = skills;
        this.autoApply       = autoApply;
        this.notifyEmail     = notifyEmail;
        this.notifySms       = notifySms;
        this.notifyJobLinks  = notifyJobLinks;
        this.phoneNumber     = phoneNumber;
    }





}
