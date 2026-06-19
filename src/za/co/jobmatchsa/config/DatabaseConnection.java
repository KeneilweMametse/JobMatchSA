package za.co.jobmatchsa.config;

import java.sql.Connection;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * za.co.jobmatchsa.config.DatabaseConnection handles the single shared connection to PostgreSQL.
 * It reads credentials from the .env file so passwords are never hardcoded.
 */
public class DatabaseConnection {

    private static Connection connection = null;

    // Private constructor — no one should instantiate this class
    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load values from your .env file
                Dotenv dotenv = Dotenv.load();

                String url      = dotenv.get("DB_URL");
                String user     = dotenv.get("DB_USER");
                String password = dotenv.get("DB_PASSWORD");

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to database successfully.");

            } catch (SQLException e) {
                System.err.println("Database connection failed: " + e.getMessage());
            }
        }
        return connection;
    }
}