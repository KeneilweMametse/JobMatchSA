import za.co.jobmatchsa.config.DatabaseConnection;

import java.sql.Connection;

/**
 * Main entry point for Job Match SA.
 * Phase 1: confirms the database connection is w orking.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   Job Match SA — Starting up   ");
        System.out.println("=================================");

        // Test the database connection
        Connection connection = DatabaseConnection.getConnection();

        if (connection != null) {
            System.out.println("Phase 1 complete: database is connected and ready.");
        } else {
            System.out.println("Could not connect to the database. Check your .env file.");
        }

        // Close connection cleanly on exit
        DatabaseConnection.closeConnection();
    }
}