package za.co.jobmatchsa.dao;

import za.co.jobmatchsa.config.DatabaseConnection;
import za.co.jobmatchsa.model.CandidateProfile;

import java.sql.*;

/**
 * CandidateProfileDAO handles all database operations
 * related to the 'candidate_profiles' table.
 *
 * Note: like UserDAO, we do NOT wrap the shared Connection in
 * try-with-resources — only PreparedStatement and ResultSet.
 */
public class CandidateProfileDAO {

    /**
     * Saves a new candidate profile linked to a user.
     * Returns true if successful, false otherwise.
     */
    public boolean createProfile(CandidateProfile profile) {
        String sql = """
            INSERT INTO candidate_profiles
            (user_id, location, years_experience, education_level, skills,
             auto_apply, notify_email, notify_sms, notify_job_links, phone_number)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, profile.getUserId());
            stmt.setString(2, profile.getLocation());
            stmt.setInt(3, profile.getYearsExperience());
            stmt.setString(4, profile.getEducationLevel());
            stmt.setString(5, profile.getSkills());
            stmt.setBoolean(6, profile.isAutoApply());
            stmt.setBoolean(7, profile.isNotifyEmail());
            stmt.setBoolean(8, profile.isNotifySms());
            stmt.setBoolean(9, profile.isNotifyJobLinks());
            stmt.setString(10, profile.getPhoneNumber());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error creating profile: " + e.getMessage());
            return false;
        }
    }

    /**
     * Finds a candidate profile by the user's ID.
     * Returns the profile if it exists, or null if the user hasn't created one yet.
     */
  public CandidateProfile findByUserId(int userId) {
    String sql = "SELECT * FROM candidate_profiles WHERE user_id = ?";

    Connection conn = DatabaseConnection.getConnection();

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, userId);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                CandidateProfile profile = new CandidateProfile();
                profile.setId(rs.getInt("id"));
                profile.setUserId(rs.getInt("user_id"));
                profile.setLocation(rs.getString("location"));
                profile.setYearsExperience(rs.getInt("years_experience"));
                profile.setEducationLevel(rs.getString("education_level"));
                profile.setSkills(rs.getString("skills"));
                profile.setAutoApply(rs.getBoolean("auto_apply"));
                profile.setNotifyEmail(rs.getBoolean("notify_email"));
                profile.setNotifySms(rs.getBoolean("notify_sms"));
                profile.setNotifyJobLinks(rs.getBoolean("notify_job_links"));
                profile.setPhoneNumber(rs.getString("phone_number"));
                return profile;
            }
        }

    } catch (SQLException e) {
        System.err.println("Error finding profile: " + e.getMessage());
    }

    return null; // user has no profile yet
}


    /**
     * Checks if a user already has a candidate profile.
     * Prevents creating duplicate profiles for the same user.
     */
    public boolean profileExists(int userId) {
        return findByUserId(userId) != null;
    }
}
