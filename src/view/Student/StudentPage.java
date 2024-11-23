package view.Student;

import controller.Student.StudentController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User.StudentModel;
import model.User.UserModel;

public class StudentPage extends Application {

    private boolean isSidebarExpanded = true;
    StudentController controller;

    @SuppressWarnings("unused")
    @Override
    public void start(Stage primaryStage) {
        // Main Layout
        BorderPane mainLayout = new BorderPane();

        // Sidebar
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(15));
        sidebar.getStyleClass().add("sidebar");

        Button toggleButton = new Button("☰");
        // ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/resources/home.png")));

        Button homeTab = createButtonWithIcon("/resources/home.png", " Home");
        Button browseTab = createButtonWithIcon("/resources/battery.png", " Browse");
        Button maintenanceTab = createButtonWithIcon("/resources/bell.png", " Maintenance");
        Button settingsTab = createButtonWithIcon("/resources/user.png", " Settings");

        homeTab.setPrefWidth(200);
        browseTab.setPrefWidth(200);
        maintenanceTab.setPrefWidth(200);
        settingsTab.setPrefWidth(200);

        // Align the text to the left
        updateButtonIconsWithText(homeTab, browseTab, maintenanceTab, settingsTab);


        sidebar.getChildren().addAll(toggleButton, homeTab, browseTab, maintenanceTab,settingsTab);
        sidebar.setMaxWidth(200);

        // Content Area
        BorderPane contentPane = new BorderPane();
        contentPane.setPadding(new Insets(15));

        Text contentText = new Text();

        // Controller
        controller = new StudentController(contentText);

        // Sidebar Navigation Events
        homeTab.setOnAction(e -> {
            homeTab.setStyle("-fx-background-color: white; -fx-text-fill: #2196F3;");
            for (Button button : new Button[]{browseTab, maintenanceTab, settingsTab}) {
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }
            
        });

        settingsTab.setOnAction(e -> {

            settingsTab.setStyle("-fx-background-color: white; -fx-text-fill: #2196F3;");
            for (Button button : new Button[]{homeTab, browseTab, maintenanceTab}) {
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }
        
            // Create fields for settings page
            Label nameLabel = new Label("Change Name:");
            TextField nameField = new TextField();
            nameField.setPromptText("Enter your new name");
            nameField.setMaxWidth(300);
            nameField.setMinHeight(40);
        
            Label emailLabel = new Label("Change Email:");
            TextField emailField = new TextField();
            emailField.setPromptText("Enter your new email");
            emailField.setMaxWidth(300);
            emailField.setMinHeight(40);
        
            Label phoneLabel = new Label("Change Phone Number:");
            TextField phoneField = new TextField();
            phoneField.setPromptText("Enter your new phone number");
            phoneField.setMaxWidth(300);
            phoneField.setMinHeight(40);
        
            Label passwordLabel = new Label("Reset Password:");
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Enter your new password");
            passwordField.setMaxWidth(300);
            passwordField.setMinHeight(40);
        
            Label passwordLabel2 = new Label("Confirm Password:");
            PasswordField passwordField2 = new PasswordField();
            passwordField2.setPromptText("Confirm your new password");
            passwordField2.setMaxWidth(300);
            passwordField2.setMinHeight(40);
        
            Button saveButton = new Button("Save Details");
            saveButton.setOnAction(ev -> controller.editProfile(nameField.getText(), emailField.getText(), phoneField.getText(), passwordField.getText(), passwordField2.getText()));
        
            // Layout adjustments
            VBox formLayout = new VBox(10, nameLabel, nameField, emailLabel, emailField, phoneLabel, phoneField, passwordLabel, passwordField, passwordLabel2, passwordField2);
            formLayout.setSpacing(15);
            formLayout.setAlignment(Pos.CENTER);
        
            // Make the save button more visually prominent and align it
            HBox buttonLayout = new HBox(saveButton);
            buttonLayout.setAlignment(Pos.CENTER);
            buttonLayout.setSpacing(10);
        
            VBox content = new VBox(20, formLayout, buttonLayout);
            content.setAlignment(Pos.CENTER);
        
            contentPane.setCenter(content);
        });

        browseTab.setOnAction(e -> {

            browseTab.setStyle("-fx-background-color: white; -fx-text-fill: #2196F3;");
            for (Button button : new Button[]{homeTab, maintenanceTab, settingsTab}) {
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }

            VBox homeLayout = new VBox(10);
            homeLayout.setPadding(new Insets(10));

            // Search Bar
            HBox searchBox = new HBox(10);
            TextField searchField = new TextField();
            searchField.setPromptText("Search...");
            searchField.setPrefWidth(300);
            searchField.setPrefHeight(50);
            Button searchButton = new Button("🔎");

            searchButton.setOnAction(ev -> controller.onSearchButtonClicked(searchField.getText()));
            searchField.textProperty().addListener((obs, oldText, newText) -> controller.onSearchTextChanged(newText));

            searchBox.getChildren().addAll(searchField, searchButton);
            searchBox.setAlignment(Pos.CENTER);

            // Scrollable Grid
            ScrollPane scrollPane = new ScrollPane();
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            scrollPane.setContent(gridPane);
            scrollPane.setFitToWidth(true);
            // make it colorless
            scrollPane.setStyle("-fx-background-color: #E3F2FD;");
            gridPane.setStyle("-fx-background-color: #E3F2FD;");

            // Link gridPane to controller for dynamic updates
            controller.setGridPane(gridPane);

            homeLayout.getChildren().addAll(searchBox, scrollPane);
            contentPane.setCenter(homeLayout);

            // Add initial content
            controller.populateInitialGridContent();

            primaryStage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                controller.populateInitialGridContent();
            });
        });

        maintenanceTab.setOnAction(e -> {

            maintenanceTab.setStyle("-fx-background-color: white; -fx-text-fill: #2196F3;");
            for (Button button : new Button[]{homeTab, browseTab, settingsTab}) {
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }

            VBox maintenanceLayout = new VBox(10);
            maintenanceLayout.setPadding(new Insets(20));
        
            TextArea complainField = new TextArea();
            complainField.setPromptText("Enter your complaint...");
            complainField.setPrefWidth(400);
            complainField.setPrefHeight(300);
        
            TextArea maintenanceField = new TextArea();
            maintenanceField.setPromptText("Maintenance Request Details...");
            maintenanceField.setPrefWidth(400);
            maintenanceField.setPrefHeight(300);
        
            Button submitButton = new Button("Submit Request");
            submitButton.setOnAction(ev -> {
                String complain = complainField.getText();
                String details = maintenanceField.getText();
                controller.onMaintenanceRequestSubmitted(complain, details);
            });
        
            ToggleButton toggleButton_complain = new ToggleButton("Switch to Maintenance");
            toggleButton_complain.setOnAction(toggleEvent -> {
                if (toggleButton_complain.isSelected()) {
                    complainField.setVisible(false);
                    complainField.setManaged(false);
                    maintenanceField.setVisible(true);
                    maintenanceField.setManaged(true);
                    toggleButton_complain.setText("Switch to Complain");
                } else {
                    complainField.setVisible(true);
                    complainField.setManaged(true);
                    maintenanceField.setVisible(false);
                    maintenanceField.setManaged(false);
                    toggleButton_complain.setText("Switch to Maintenance");
                }
            });
        
            maintenanceField.setVisible(false);
            maintenanceField.setManaged(false);
        
            // Add elements to the layout
            maintenanceLayout.getChildren().addAll(
                new Text("Complain Maintenance Request"),
                toggleButton_complain,
                complainField,
                maintenanceField,
                submitButton
            );
            maintenanceLayout.setAlignment(Pos.TOP_RIGHT);

            contentPane.setCenter(maintenanceLayout);
        });
         
        // Toggle Sidebar Expand/Collapse
        toggleButton.setOnAction(e -> {
            if (isSidebarExpanded) {
                sidebar.setPrefWidth(50);
                for (Button button : new Button[]{homeTab, browseTab, maintenanceTab,settingsTab}) {
                    button.setMinWidth(50);
                    button.setMaxWidth(50);
                }
                updateButtonIconsOnly(homeTab, browseTab, maintenanceTab,settingsTab);
                toggleButton.setText("☰");
            } else {
                sidebar.setPrefWidth(200);
                for (Button button : new Button[]{homeTab, browseTab, maintenanceTab, settingsTab}) {
                    button.setMinWidth(200);
                    button.setMaxWidth(Double.MAX_VALUE);
                }
                updateButtonIconsWithText(homeTab, browseTab, maintenanceTab, settingsTab);
                toggleButton.setText("✖");
            }
            isSidebarExpanded = !isSidebarExpanded;
        });

        // Layout Structure
        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(contentPane);

        // Scene
        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Student Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize with the home page
        homeTab.fire();
    }

    private Button createButtonWithIcon(String iconPath, String text) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
        icon.setFitWidth(20);
        icon.setFitHeight(20);
        Button button = new Button(text, icon);
        button.getStyleClass().add("sidebar-button");
        return button;
    }

    private void updateButtonIconsOnly(Button... buttons) {
        for (Button button : buttons) {
            button.setText("");
        }
    }

    private void updateButtonIconsWithText(Button... buttons) {
        String[][] buttonData = {
            {"apps.png", " Home"},
            {"apps.png", " Browse"},
            {"apps.png", " Maintenance"},
            {"apps.png", " Settings"}
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(buttonData[i][1]);
            buttons[i].setAlignment(Pos.CENTER_LEFT);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setStudent(StudentModel user) {
        controller.setStudent(user);
    }
}
