package za.co.jobmatchsa.model;

import java.time.LocalDateTime;

/**
 * Application tracks every job application submitted for a user.
 * Maps to the 'applications' table in the database.
 */
public class Application {

    private int id;
    private int userId;
    private int jobId;
    private String status;      // "pending", "submitted", "failed"
    private String method;      // "auto-form", "email", "manual"
    private LocalDateTime appliedAt;

    // Default constructor
    public Application() {
    }

    // Constructor for creating a new application record
    public Application(int userId, int jobId, String status, String method) {
        this.userId = userId;
        this.jobId = jobId;
        this.status = status;
        this.method = method;
    }
}