package za.co.jobmatchsa;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import za.co.jobmatchsa.config.DatabaseConnection;
import za.co.jobmatchsa.gui.ProfileFormController;
import za.co.jobmatchsa.model.CandidateProfile;
import za.co.jobmatchsa.model.User;
import za.co.jobmatchsa.service.AuthService;
import za.co.jobmatchsa.service.CvGeneratorService;
import za.co.jobmatchsa.service.MatchingService;
import za.co.jobmatchsa.service.MatchingService.JobMatch;
import za.co.jobmatchsa.service.ProfileService;

import java.util.List;
import java.util.Map;

public class JobMatchApp extends Application {

    private final AuthService authService = new AuthService();
    private final ProfileService profileService = new ProfileService();
    private final CvGeneratorService cvGeneratorService = new CvGeneratorService();
    private final MatchingService matchingService = new MatchingService();

    @Override
    public void start(Stage primaryStage) {
        showLoginScreen(primaryStage);
    }

    // ---- LOGIN SCREEN ----
    private void showLoginScreen(Stage stage) {
        Label title = new Label("Job Match SA");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 26));

        Label subtitle = new Label("Find your perfect job match");
        subtitle.setStyle("-fx-text-fill: #666;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-min-width: 300;");

        Button registerButton = new Button("Create Account");
        registerButton.setStyle("-fx-min-width: 300;");

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: red;");

        loginButton.setOnAction(e -> {
            User user = authService.login(emailField.getText(), passwordField.getText());
            if (user != null) {
                goToProfileOrDashboard(stage, user);
            } else {
                statusLabel.setText("Login failed. Check your email and password.");
            }
        });

        registerButton.setOnAction(e -> showRegisterScreen(stage));

        VBox layout = new VBox(12, title, subtitle, emailField,
                passwordField, loginButton, registerButton, statusLabel);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f5f5f5;");

        stage.setTitle("Job Match SA");
        stage.setScene(new Scene(layout, 420, 400));
        stage.show();
    }

    // ---- REGISTER SCREEN ----
    private void showRegisterScreen(Stage stage) {
        Label title = new Label("Create Your Account");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");
        nameField.setMaxWidth(300);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password (min 6 characters)");
        passwordField.setMaxWidth(300);

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-min-width: 300;");

        Button backButton = new Button("Back to Login");
        backButton.setStyle("-fx-min-width: 300;");

        Label statusLabel = new Label();

        registerButton.setOnAction(e -> {
            String result = authService.register(nameField.getText(),
                    emailField.getText(), passwordField.getText());
            if (result.contains("successful")) {
                statusLabel.setStyle("-fx-text-fill: green;");
                statusLabel.setText(result + " You can now log in.");
            } else {
                statusLabel.setStyle("-fx-text-fill: red;");
                statusLabel.setText(result);
            }
        });

        backButton.setOnAction(e -> showLoginScreen(stage));

        VBox layout = new VBox(12, title, nameField, emailField,
                passwordField, registerButton, backButton, statusLabel);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f5f5f5;");

        stage.setTitle("Job Match SA — Register");
        stage.setScene(new Scene(layout, 420, 420));
        stage.show();
    }

    // ---- ROUTE AFTER LOGIN ----
    private void goToProfileOrDashboard(Stage stage, User user) {
        CandidateProfile profile = profileService.getProfile(user.getId());
        if (profile != null) {
            showDashboard(stage, user, profile);
        } else {
            ProfileFormController form = new ProfileFormController(user, () -> {
                CandidateProfile newProfile = profileService.getProfile(user.getId());
                showDashboard(stage, user, newProfile);
            });
            form.show(stage);
        }
    }

    // ---- DASHBOARD ----
    private void showDashboard(Stage stage, User user, CandidateProfile profile) {
        Label title = new Label("Welcome, " + user.getFullName() + "!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label skillsLabel = new Label("Your skills: " + profile.getSkills());
        skillsLabel.setStyle("-fx-text-fill: #555;");

        Button matchJobsButton = new Button("🔍  Find Matching Jobs");
        matchJobsButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-min-width: 280; -fx-pref-height: 40;");

        Button generateCvButton = new Button("📄  Generate ATS CV");
        generateCvButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-min-width: 280; -fx-pref-height: 40;");

        Button skillGapButton = new Button("📊  View Skill Gap Analysis");
        skillGapButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-min-width: 280; -fx-pref-height: 40;");

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: green;");

        matchJobsButton.setOnAction(e -> showJobMatches(stage, user, profile));

        generateCvButton.setOnAction(e -> {
            String path = cvGeneratorService.generateBaseCv(user, profile);
            if (path != null) {
                statusLabel.setText("CV saved to: " + path);
            } else {
                statusLabel.setStyle("-fx-text-fill: red;");
                statusLabel.setText("CV generation failed.");
            }
        });

        skillGapButton.setOnAction(e -> showSkillGapAnalysis(stage, user, profile));

        VBox layout = new VBox(15, title, skillsLabel,
                matchJobsButton, generateCvButton, skillGapButton, statusLabel);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f5f5f5;");

        stage.setTitle("Job Match SA — Dashboard");
        stage.setScene(new Scene(layout, 420, 380));
        stage.show();
    }

    // ---- JOB MATCHES SCREEN ----
    private void showJobMatches(Stage stage, User user, CandidateProfile profile) {
        Label title = new Label("Job Matches for " + user.getFullName());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        List<JobMatch> matches = matchingService.findMatches(profile);

        VBox jobList = new VBox(10);

        if (matches.isEmpty()) {
            jobList.getChildren().add(new Label("No jobs found. Make sure jobs are seeded in the database."));
        } else {
            for (JobMatch match : matches) {
                VBox card = new VBox(4);
                card.setPadding(new Insets(10));
                card.setStyle("-fx-background-color: white; -fx-border-color: #ddd; " +
                        "-fx-border-radius: 6; -fx-background-radius: 6;");

                String scoreColor = match.matchScore >= 70 ? "#4CAF50" :
                        match.matchScore >= 40 ? "#FF9800" : "#f44336";

                Label jobTitle = new Label(match.job.getTitle() + " — " + match.job.getCompany());
                jobTitle.setFont(Font.font("Arial", FontWeight.BOLD, 13));

                Label score = new Label("Match: " + match.matchScore + "%");
                score.setStyle("-fx-text-fill: " + scoreColor + "; -fx-font-weight: bold;");

                Label location = new Label("📍 " + match.job.getLocation() +
                        "   💰 " + match.job.getSalaryRange());
                location.setStyle("-fx-text-fill: #555; -fx-font-size: 11;");

                Label matched = new Label("✅ Matched: " + String.join(", ", match.matchedSkills));
                matched.setStyle("-fx-text-fill: #4CAF50; -fx-font-size: 11;");

                Label missing = new Label("❌ Missing: " + String.join(", ", match.missingSkills));
                missing.setStyle("-fx-text-fill: #f44336; -fx-font-size: 11;");

                Label source = new Label("Source: " + match.job.getSource());
                source.setStyle("-fx-text-fill: #888; -fx-font-size: 10;");

                card.getChildren().addAll(jobTitle, score, location, matched, missing, source);
                jobList.getChildren().add(card);
            }
        }

        ScrollPane scrollPane = new ScrollPane(jobList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(5));

        Button backButton = new Button("← Back to Dashboard");
        backButton.setOnAction(e -> showDashboard(stage, user, profile));

        VBox layout = new VBox(12, title, scrollPane, backButton);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");

        stage.setTitle("Job Match SA — Job Matches");
        stage.setScene(new Scene(layout, 500, 600));
        stage.show();
    }

    // ---- SKILL GAP ANALYSIS SCREEN ----
    private void showSkillGapAnalysis(Stage stage, User user, CandidateProfile profile) {
        Label title = new Label("Skill Gap Analysis");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label subtitle = new Label("Skills you're missing across the most job listings:");
        subtitle.setStyle("-fx-text-fill: #555;");

        Map<String, Integer> gaps = matchingService.getTopMissingSkills(profile, 8);

        VBox gapList = new VBox(8);

        if (gaps.isEmpty()) {
            gapList.getChildren().add(new Label("No skill gaps found — great match!"));
        } else {
            for (Map.Entry<String, Integer> entry : gaps.entrySet()) {
                HBox row = new HBox(10);
                row.setPadding(new Insets(8));
                row.setStyle("-fx-background-color: white; -fx-border-color: #ddd; " +
                        "-fx-border-radius: 6; -fx-background-radius: 6;");
                row.setAlignment(Pos.CENTER_LEFT);

                Label skillName = new Label(entry.getKey());
                skillName.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                skillName.setMinWidth(150);

                Label count = new Label("Missing in " + entry.getValue() + " job(s)");
                count.setStyle("-fx-text-fill: #f44336;");

                row.getChildren().addAll(skillName, count);
                gapList.getChildren().add(row);
            }
        }

        Label tip = new Label("💡 Consider upskilling in these areas to improve your match rate.");
        tip.setStyle("-fx-text-fill: #2196F3; -fx-font-style: italic;");
        tip.setWrapText(true);

        Button backButton = new Button("← Back to Dashboard");
        backButton.setOnAction(e -> showDashboard(stage, user, profile));

        VBox layout = new VBox(12, title, subtitle, gapList, tip, backButton);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: #f5f5f5;");

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);

        stage.setTitle("Job Match SA — Skill Gap Analysis");
        stage.setScene(new Scene(scrollPane, 480, 520));
        stage.show();
    }

    @Override
    public void stop() {
        DatabaseConnection.closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}