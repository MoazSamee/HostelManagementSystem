package view.Student;

import controller.Student.StudentController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
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

        sidebar.getChildren().addAll(toggleButton, homeTab, settingsTab, otherTab);
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

            // On the window resize, recalculate the number of columns
            // This one is fast but not working properly
            primaryStage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                controller.populateInitialGridContent();
            });
            //// this one is working better but it is slower
            // gridPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            //     controller.populateInitialGridContent();
            // });
        });

        settingsTab.setOnAction(e -> {
            contentText.setText("This is the Settings Page.");
            contentPane.setCenter(new VBox(contentText));
        });

        otherTab.setOnAction(e -> {
            contentText.setText("This is the Other Page.");
            contentPane.setCenter(new VBox(contentText));
        });

        // Toggle Sidebar Expand/Collapse
        toggleButton.setOnAction(e -> {
            if (isSidebarExpanded) {
                sidebar.setPrefWidth(50);
                for (Button button : new Button[]{homeTab, settingsTab, otherTab}) {
                    button.setMinWidth(50);
                    button.setMaxWidth(50);
                }
                updateButtonIconsOnly(homeTab, settingsTab, otherTab);
                toggleButton.setText("☰");
            } else {
                sidebar.setPrefWidth(200);
                for (Button button : new Button[]{homeTab, settingsTab, otherTab}) {
                    button.setMinWidth(200);
                    button.setMaxWidth(Double.MAX_VALUE);
                }
                updateButtonIconsWithText(homeTab, settingsTab, otherTab);
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
            {"apps.png", "Other"}
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(buttonData[i][1]);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
