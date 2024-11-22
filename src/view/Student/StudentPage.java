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

public class StudentPage extends Application {

    private boolean isSidebarExpanded = true;

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
        Button homeTab = createButtonWithIcon("apps.png", "Home");
        Button settingsTab = createButtonWithIcon("apps.png", "Settings");
        Button otherTab = createButtonWithIcon("apps.png", "Other");
        Button maintenanceTab = createButtonWithIcon("apps.png", "Maintenance");

        toggleButton.setPrefWidth(50);

        sidebar.getChildren().addAll(toggleButton, homeTab, settingsTab, otherTab, maintenanceTab);
        sidebar.setMaxWidth(200);

        // Content Area
        BorderPane contentPane = new BorderPane();
        contentPane.setPadding(new Insets(15));

        Text contentText = new Text();

        // Controller
        StudentController controller = new StudentController(contentText);

        // Sidebar Navigation Events
        homeTab.setOnAction(e -> {
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

        settingsTab.setOnAction(e -> {
            contentText.setText("This is the Settings Page.");
            contentPane.setCenter(new VBox(contentText));
        });

        otherTab.setOnAction(e -> {
            contentText.setText("This is the Other Page.");
            contentPane.setCenter(new VBox(contentText));
        });

        maintenanceTab.setOnAction(e -> {
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
                for (Button button : new Button[]{homeTab, settingsTab, otherTab, maintenanceTab}) {
                    button.setMinWidth(50);
                    button.setMaxWidth(50);
                }
                updateButtonIconsOnly(homeTab, settingsTab, otherTab, maintenanceTab);
                toggleButton.setText("☰");
            } else {
                sidebar.setPrefWidth(200);
                for (Button button : new Button[]{homeTab, settingsTab, otherTab, maintenanceTab}) {
                    button.setMinWidth(200);
                    button.setMaxWidth(Double.MAX_VALUE);
                }
                updateButtonIconsWithText(homeTab, settingsTab, otherTab, maintenanceTab);
                toggleButton.setText("✖");
            }
            isSidebarExpanded = !isSidebarExpanded;
        });

        // Layout Structure
        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(contentPane);

        // Scene
        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

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
            {"apps.png", "Home"},
            {"apps.png", "Settings"},
            {"apps.png", "Other"},
            {"apps.png", "Maintenance"}
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(buttonData[i][1]);
            buttons[i].setAlignment(Pos.CENTER_LEFT);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
