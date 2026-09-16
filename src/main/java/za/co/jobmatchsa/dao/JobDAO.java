package za.co.jobmatchsa.dao;

import za.co.jobmatchsa.config.DatabaseConnection;
import za.co.jobmatchsa.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {

    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM jobs ORDER BY posted_at DESC";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                jobs.add(mapRowToJob(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching jobs: " + e.getMessage());
        }
        return jobs;
    }

    private Job mapRowToJob(ResultSet rs) throws SQLException {
        Job job = new Job();
        job.setId(rs.getInt("id"));
        job.setTitle(rs.getString("title"));
        job.setCompany(rs.getString("company"));
        job.setLocation(rs.getString("location"));
        job.setDescription(rs.getString("description"));
        job.setRequiredSkills(rs.getString("required_skills"));
        job.setSource(rs.getString("source"));
        job.setSourceUrl(rs.getString("source_url"));
        job.setSalaryRange(rs.getString("salary_range"));
        return job;
    }
}