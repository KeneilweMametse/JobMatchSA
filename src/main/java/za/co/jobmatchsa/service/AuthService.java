package za.co.jobmatchsa.service;

import za.co.jobmatchsa.dao.UserDAO;
import za.co.jobmatchsa.model.User;
import org.mindrot.jbcrypt.BCrypt;

/**
 * AuthService handles the business logic for registering and logging in users.
 * This is where password hashing and validation rules live —
 * keeping this logic separate from the database code (UserDAO) and
 * separate from the user interface (Main).
 */
public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Registers a new user.
     * Hashes the password before storing it — the plain password is never saved.
     *
     * Returns a message indicating success or the reason for failure.
     */
    public String register(String fullName, String email, String plainPassword) {

        // Basic validation
        if (fullName == null || fullName.isBlank()) {
            return "Full name cannot be empty.";
        }
        if (email == null || !email.contains("@")) {
            return "Please enter a valid email address.";
        }
        if (plainPassword == null || plainPassword.length() < 6) {
            return "Password must be at least 6 characters long.";
        }

        // Check if email is already registered
        if (userDAO.emailExists(email)) {
            return "An account with this email already exists.";
        }

        // Hash the password before storing — BCrypt automatically generates
        // a random "salt" each time, making every hash unique even for the same password
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        User newUser = new User(fullName, email, hashedPassword);
        int userId = userDAO.registerUser(newUser);

        if (userId != -1) {
            return "Registration successful! Your user ID is " + userId;
        } else {
            return "Registration failed. Please try again.";
        }
    }

    /**
     * Logs in a user by checking their email and password.
     * Returns the User object if login is successful, or null if it fails.
     */
    public User login(String email, String plainPassword) {

        User user = userDAO.findByEmail(email);

        if (user == null) {
            System.out.println("No account found with that email.");
            return null;
        }

        // BCrypt.checkpw compares the typed password against the stored hash
        // It re-hashes the input and checks if it matches — the original
        // password is never stored or compared directly
        boolean passwordMatches = BCrypt.checkpw(plainPassword, user.getPassword());

        if (passwordMatches) {
            System.out.println("Login successful. Welcome, " + user.getFullName() + "!");
            return user;
        } else {
            System.out.println("Incorrect password.");
            return null;
        }
    }
}