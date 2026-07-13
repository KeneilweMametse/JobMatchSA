package za.co.jobmatchsa.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import za.co.jobmatchsa.model.User;
import za.co.jobmatchsa.service.ProfileService;

public class ProfileFormController {

    private final ProfileService profileService = new ProfileService();
    private final User loggedInUser;
    private final Runnable onProfileSaved;

    public ProfileFormController(User loggedInUser, Runnable onProfileSaved) {
        this.loggedInUser = loggedInUser;
        this.onProfileSaved = onProfileSaved;
    }

    public void show(Stage stage) {

        Label title = new Label("Set Up Your Candidate Profile");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label locationLabel = new Label("Location:");
        ComboBox<String> locationBox = new ComboBox<>();
        locationBox.getItems().addAll("Johannesburg", "Pretoria", "Cape Town",
                "Durban", "Bloemfontein", "Port Elizabeth", "East London", "Soweto", "Other");
        locationBox.setPromptText("Select your location");

        Label experienceLabel = new Label("Years of Experience:");
        ComboBox<String> experienceBox = new ComboBox<>();
        experienceBox.getItems().addAll("0 - Entry level", "1-2 years",
                "3-5 years", "6-10 years", "10+ years");
        experienceBox.setPromptText("Select experience range");

        Label educationLabel = new Label("Highest Qualification:");
        ComboBox<String> educationBox = new ComboBox<>();
        educationBox.getItems().addAll("Matric / Grade 12", "Certificate",
                "Diploma", "Bachelor's Degree", "Honours Degree",
                "Master's Degree", "Doctorate (PhD)");
        educationBox.setPromptText("Select your qualification");

        Label skillsLabel = new Label("Skills (comma-separated):");
        TextField skillsField = new TextField();
        skillsField.setPromptText("e.g. Java, SQL, Python");

        Label preferencesLabel = new Label("Preferences");
        preferencesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        CheckBox autoApplyBox = new CheckBox("Enable auto-apply");
        CheckBox notifyEmailBox = new CheckBox("Email notifications");
        CheckBox notifySmsBox = new CheckBox("SMS notifications");
        CheckBox notifyJobLinksBox = new CheckBox("Receive job links");

        Label phoneLabel = new Label("Phone Number (for SMS):");
        TextField phoneField = new TextField();
        phoneField.setPromptText("e.g. 0821234567");
        phoneField.setDisable(true);

        notifySmsBox.setOnAction(e -> phoneField.setDisable(!notifySmsBox.isSelected()));

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: red;");

        Button saveButton = new Button("Save Profile");
        saveButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");

        saveButton.setOnAction(e -> {
            if (locationBox.getValue() == null || educationBox.getValue() == null
                    || experienceBox.getValue() == null) {
                statusLabel.setText("Please fill in all required fields.");
                return;
            }
            if (skillsField.getText().isBlank()) {
                statusLabel.setText("Please list at least one skill.");
                return;
            }
            if (notifySmsBox.isSelected() && phoneField.getText().isBlank()) {
                statusLabel.setText("Phone number required for SMS.");
                return;
            }

            int yearsExperience = mapExperience(experienceBox.getValue());

            String result = profileService.createProfile(
                    loggedInUser.getId(), locationBox.getValue(), yearsExperience,
                    educationBox.getValue(), skillsField.getText(),
                    autoApplyBox.isSelected(), notifyEmailBox.isSelected(),
                    notifySmsBox.isSelected(), notifyJobLinksBox.isSelected(),
                    phoneField.getText()
            );

            if (result.contains("successfully")) {
                stage.close();
                if (onProfileSaved != null) onProfileSaved.run();
            } else {
                statusLabel.setStyle("-fx-text-fill: red;");
                statusLabel.setText(result);
            }
        });

        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.setPadding(new Insets(10, 0, 10, 0));
        form.add(locationLabel, 0, 0);    form.add(locationBox, 1, 0);
        form.add(experienceLabel, 0, 1);  form.add(experienceBox, 1, 1);
        form.add(educationLabel, 0, 2);   form.add(educationBox, 1, 2);
        form.add(skillsLabel, 0, 3);      form.add(skillsField, 1, 3);
        form.add(phoneLabel, 0, 4);       form.add(phoneField, 1, 4);

        VBox layout = new VBox(12, title, form, preferencesLabel,
                autoApplyBox, notifyEmailBox, notifySmsBox,
                notifyJobLinksBox, saveButton, statusLabel);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.TOP_LEFT);

        stage.setTitle("Job Match SA — Candidate Profile");
        stage.setScene(new Scene(layout, 480, 580));
        stage.show();
    }

    private int mapExperience(String range) {
        return switch (range) {
            case "0 - Entry level" -> 0;
            case "1-2 years" -> 1;
            case "3-5 years" -> 4;
            case "6-10 years" -> 8;
            case "10+ years" -> 12;
            default -> 0;
        };
    }
}