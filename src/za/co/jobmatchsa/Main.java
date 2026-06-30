package za.co.jobmatchsa;

import za.co.jobmatchsa.config.DatabaseConnection;
import za.co.jobmatchsa.model.User;
import za.co.jobmatchsa.service.AuthService;

import java.util.Scanner;

/**
 * Main entry point for Job Match SA.
 * Phase 2: console-based user registration and login.
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AuthService authService = new AuthService();

        System.out.println("=================================");
        System.out.println("       Job Match SA");
        System.out.println("=================================");

        boolean running = true;

        while (running) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1" -> {
                    System.out.print("Full name: ");
                    String fullName = scanner.nextLine();

                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Password (min 6 characters): ");
                    String password = scanner.nextLine();

                    String result = authService.register(fullName, email, password);
                    System.out.println(result);
                }

                case "2" -> {
                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    User loggedInUser = authService.login(email, password);

                    if (loggedInUser != null) {
                        System.out.println("You are now logged in as " + loggedInUser.getFullName());
                        // Phase 3 onwards: this is where we'd go to the profile/dashboard
                    }
                }

                case "3" -> {
                    running = false;
                    System.out.println("Goodbye!");
                }

                default -> System.out.println("Invalid option, please choose 1, 2 or 3.");
            }
        }

        DatabaseConnection.closeConnection();
        scanner.close();
    }
}