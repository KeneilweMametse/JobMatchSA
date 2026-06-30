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

    // Getters
    public int getId()                  { return id; }
    public int getUserId()              { return userId; }
    public String getLocation()         { return location; }
    public int getYearsExperience()     { return yearsExperience; }
    public String getEducationLevel()   { return educationLevel; }
    public String getSkills()           { return skills; }
    public boolean isAutoApply()        { return autoApply; }
    public boolean isNotifyEmail()      { return notifyEmail; }
    public boolean isNotifySms()        { return notifySms; }
    public boolean isNotifyJobLinks()   { return notifyJobLinks; }
    public String getPhoneNumber()      { return phoneNumber; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(int id)                          { this.id = id; }
    public void setUserId(int userId)                  { this.userId = userId; }
    public void setLocation(String location)           { this.location = location; }
    public void setYearsExperience(int years)          { this.yearsExperience = years; }
    public void setEducationLevel(String level)        { this.educationLevel = level; }
    public void setSkills(String skills)               { this.skills = skills; }
    public void setAutoApply(boolean autoApply)        { this.autoApply = autoApply; }
    public void setNotifyEmail(boolean notifyEmail)    { this.notifyEmail = notifyEmail; }
    public void setNotifySms(boolean notifySms)        { this.notifySms = notifySms; }
    public void setNotifyJobLinks(boolean links)       { this.notifyJobLinks = links; }
    public void setPhoneNumber(String phoneNumber)     { this.phoneNumber = phoneNumber; }
    public void setUpdatedAt(LocalDateTime updatedAt)  { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "CandidateProfile{userId=" + userId +
                ", location='" + location + "'" +
                ", skills='" + skills + "'" +
                ", autoApply=" + autoApply + "}";
    }
}


