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
}