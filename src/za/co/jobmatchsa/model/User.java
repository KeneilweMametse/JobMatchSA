package za.co.jobmatchsa.model;

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

