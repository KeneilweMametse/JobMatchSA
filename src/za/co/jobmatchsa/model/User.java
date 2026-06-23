package za.co.jobmatch.model;

import java.time.LocalDateTime;

/**
 * User represents a registered account in Job Match SA.
 * Maps directly to the 'users' table in the database.
 */
public class User {

    private int id;
    private String fullName;
    private String email;
    private String password;           // always store hashed — never plain text
    private LocalDateTime createdAt;

    // Default constructor
    public User() {}

    // Constructor for creating a new user (before saving to DB)
    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email    = email;
        this.password = password;
    }

    // Full constructor (after reading from DB)
    public User(int id, String fullName, String email, String password, LocalDateTime createdAt) {
        this.id        = id;
        this.fullName  = fullName;
        this.email     = email;
        this.password  = password;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId()                   { return id; }
    public String getFullName()          { return fullName; }
    public String getEmail()             { return email; }
    public String getPassword()          { return password; }
    public LocalDateTime getCreatedAt()  { return createdAt; }

    // Setters
    public void setId(int id)                          { this.id = id; }
    public void setFullName(String fullName)           { this.fullName = fullName; }
    public void setEmail(String email)                 { this.email = email; }
    public void setPassword(String password)           { this.password = password; }
    public void setCreatedAt(LocalDateTime createdAt)  { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "User{id=" + id + ", fullName='" + fullName + "', email='" + email + "'}";
    }
}