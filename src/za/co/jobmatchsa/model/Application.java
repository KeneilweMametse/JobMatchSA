package za.co.jobmatch.model;

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
    public Application() {}

    // Constructor for creating a new application record
    public Application(int userId, int jobId, String status, String method) {
        this.userId = userId;
        this.jobId  = jobId;
        this.status = status;
        this.method = method;
    }

    // Getters
    public int getId()                   { return id; }
    public int getUserId()               { return userId; }
    public int getJobId()                { return jobId; }
    public String getStatus()            { return status; }
    public String getMethod()            { return method; }
    public LocalDateTime getAppliedAt()  { return appliedAt; }

    // Setters
    public void setId(int id)                         { this.id = id; }
    public void setUserId(int userId)                 { this.userId = userId; }
    public void setJobId(int jobId)                   { this.jobId = jobId; }
    public void setStatus(String status)              { this.status = status; }
    public void setMethod(String method)              { this.method = method; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }
