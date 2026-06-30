package za.co.jobmatchsa.dao;

import za.co.jobmatchsa.config.DatabaseConnection;
import za.co.jobmatchsa.model.User;

import java.sql.*;

/**
 * UserDAO (Data Access Object) handles all database operations
 * related to the 'users' table — saving, finding, and checking users.
 *
 * IMPORTANT: We do NOT use try-with-resources on the Connection here.
 * DatabaseConnection.getConnection() returns one SHARED connection used
 * across the whole app. Try-with-resources would auto-close it after every
 * single query, breaking all future queries. We only close PreparedStatement
 * and ResultSet, which are safe to close after each use.
 */
public class UserDAO {

    /**
     * Saves a new user to the database.
     * Returns the generated user ID if successful, or -1 if it fails.
     */
    public int registerUser(User user) {
        String sql = "INSERT INTO users (full_name, email, password) VALUES (?, ?, ?)";

        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword()); // already hashed before this point

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // returns the new user's ID
                }
            }

        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
        }

        return -1;
    }

    /**
     * Finds a user by their email address.
     * Returns the User object if found, or null if not found.
     */
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }

        } catch (SQLException e) {
            System.err.println("Error finding user: " + e.getMessage());
        }

        return null; // no user found with that email
    }

    /**
     * Checks if an email is already registered.
     * Used to prevent duplicate accounts.
     */
    public boolean emailExists(String email) {
        return findByEmail(email) != null;
    }
}