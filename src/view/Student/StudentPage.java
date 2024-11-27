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

public class StudentPage extends Application{

    private boolean isSidebarExpanded = true;
    StudentController controller;

    private Button homeTab;
    private Button browseTab;
    private Button maintenanceTab;
    private Button settingsTab;

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

        homeTab = createButtonWithIcon("/resources/home.png", " Home");
        browseTab = createButtonWithIcon("/resources/battery.png", " Browse");
        maintenanceTab = createButtonWithIcon("/resources/bell.png", " Maintenance");
        settingsTab = createButtonWithIcon("/resources/user.png", " Settings");

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


        // Sidebar Navigation Events
        homeTab.setOnAction(e -> {
            homeTab.setStyle("-fx-background-color: white; -fx-text-fill: #2196F3;");
            for (Button button : new Button[]{browseTab, maintenanceTab, settingsTab}) {
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }
            
            VBox container = ceateStudentHomePage();

            contentPane.setCenter(container);
        });

        settingsTab.setOnAction(e -> {

            settingsTab.setStyle("-fx-background-color: white; -fx-text-fill: #2196F3;");
            for (Button button : new Button[]{homeTab, browseTab, maintenanceTab}) {
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }
        
            VBox container = createSettingsPage();

            contentPane.setCenter(container);
        });

        browseTab.setOnAction(e -> {

            browseTab.setStyle("-fx-background-color: white; -fx-text-fill: #2196F3;");
            for (Button button : new Button[]{homeTab, maintenanceTab, settingsTab}) {
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }

            VBox container = createbrowseTab();
            
            contentPane.setCenter(container);
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
                String maintenance = maintenanceField.getText();
                controller.onMaintenanceRequestSubmitted(complain, maintenance);
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
        Scene scene = new Scene(mainLayout, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Student Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();

        // controller.populateInitialGridContent();

    }

    @SuppressWarnings("unused")
    private VBox createbrowseTab() {
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
        gridPane.setPadding(new Insets(10));

        scrollPane.setContent(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #E3F2FD;");
        gridPane.setStyle("-fx-background-color: #E3F2FD;");

        // Link gridPane to controller for dynamic updates
        controller.setGridPane(gridPane);

        homeLayout.getChildren().addAll(searchBox, scrollPane);
        

        gridPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            controller.refreshGridContent(newWidth.intValue());
        });
        
        VBox container = new VBox(homeLayout);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.TOP_LEFT);
        container.getStyleClass().add("container");

        return container;
    }

    @SuppressWarnings("unused")
    private VBox createSettingsPage() {
        // Create fields for settings page
        // Label nameLabel = new Label("Change Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your new name");
        nameField.setMaxWidth(400);
        nameField.setMinHeight(40);
      
        // Label emailLabel = new Label("Change Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your new email");
        emailField.setMaxWidth(300);
        emailField.setPrefWidth(195);
        emailField.setMinHeight(40);
      
        // Label phoneLabel = new Label("Change Phone Number:");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your new phone number");
        phoneField.setMaxWidth(300);
        phoneField.setPrefWidth(195);
        phoneField.setMinHeight(40);

        // Label universityLabel = new Label("Change University:");
        TextField universityField = new TextField();
        universityField.setPromptText("Enter your University");
        universityField.setMaxWidth(400);
        universityField.setMinHeight(40);

        // Label addressLabel = new Label("Change Address:");
        TextField addressField = new TextField();
        addressField.setPromptText("Enter your new Address");
        addressField.setMaxWidth(400);
        addressField.setMinHeight(40);

        // Label orgAdressLabel = new Label("Change Organization Adress:");
        TextField orgAdressField = new TextField();
        orgAdressField.setPromptText("Enter your new Organization Adress");
        orgAdressField.setMaxWidth(400);
        orgAdressField.setMinHeight(40);
      
        // Label passwordLabel = new Label("Reset Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your new password");
        passwordField.setMaxWidth(300);
        passwordField.setPrefWidth(195);
        passwordField.setMinHeight(40);
      
        // Label passwordLabel2 = new Label("Confirm Password:");
        PasswordField passwordField2 = new PasswordField();
        passwordField2.setPromptText("Confirm your new password");
        passwordField2.setMaxWidth(300);
        passwordField2.setPrefWidth(195);
        passwordField2.setMinHeight(40);
      
        Button saveButton = new Button("Save Details");
        saveButton.setOnAction(ev -> controller.editProfile(nameField.getText(), emailField.getText(),
                                    phoneField.getText(),universityField.getText(),addressField.getText(),
                                    orgAdressField.getText(), passwordField.getText(), passwordField2.getText()));
      

        HBox passwordGroup = new HBox(10, passwordField, passwordField2);
        passwordGroup.setAlignment(Pos.CENTER);
        HBox contactGroup = new HBox(10, emailField, phoneField);
        contactGroup.setAlignment(Pos.CENTER);

        // Layout adjustments
        VBox formLayout = new VBox(10, nameField, contactGroup, universityField, addressField,orgAdressField,passwordGroup);
        // VBox formLayout = new VBox(10, nameLabel, nameField, emailLabel, emailField, phoneLabel, phoneField, passwordLabel, passwordField, passwordLabel2, passwordField2);
        formLayout.setSpacing(15);
        formLayout.setAlignment(Pos.CENTER);
      
        // Make the save button more visually prominent and align it
        HBox buttonLayout = new HBox(saveButton);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setSpacing(10);
      
        VBox content = new VBox(20, formLayout, buttonLayout);
        content.setAlignment(Pos.CENTER);

        VBox container = new VBox(content);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.TOP_LEFT);
        container.getStyleClass().add("container");
        return container;
    }

    private VBox ceateStudentHomePage() {
        // Create labels for student details
        Label nameLabel = new Label("Welcome " + controller.getStudentName()+ "! 😊");
        nameLabel.setStyle("-fx-font: 36 arial; -fx-font-weight: bold; -fx-text-fill: white;");
        Label emailLabel = new Label("📧 "+controller.getStudentEmail());
        emailLabel.setStyle("-fx-font: 24 arial; -fx-text-fill: white;");
        Label phoneLabel = new Label("📞 "+ controller.getStudentPhone());
        phoneLabel.setStyle("-fx-font: 24 arial; -fx-text-fill: white;");
        Label universityLabel = new Label("University/Job: " + controller.getStudentUniversity());
        universityLabel.setStyle("-fx-font: 20 arial; -fx-text-fill: white;");
        Label addressLabel = new Label("Address: " + controller.getStudentAddress());
        addressLabel.setStyle("-fx-font: 20 arial; -fx-text-fill: white;");
        Label orgAddressLabel = new Label("Organization Address: " + controller.getStudentOrgAddress());
        orgAddressLabel.setStyle("-fx-font: 20 arial; -fx-text-fill: white;");
        Label curruntHostel = new Label("Current Hostel: " + controller.getCurruntStudentHostelname());
        curruntHostel.setStyle("-fx-font: 20 arial; -fx-text-fill: white;");
        Label roomNo = new Label("Room No: " + controller.getCurruntStudentHostelRoom());
        roomNo.setStyle("-fx-font: 20 arial; -fx-text-fill: white;");
        Label hostelAddress = new Label("Hostel Address: " + controller.getCurruntStudentHostelAdress());
        hostelAddress.setStyle("-fx-font: 20 arial; -fx-text-fill: white;");

        // Create a VBox for the details and style it
        VBox detailsLayout = new VBox(10, nameLabel, emailLabel, phoneLabel);
        detailsLayout.setPadding(new Insets(20));
        detailsLayout.setAlignment(Pos.CENTER);
        detailsLayout.getStyleClass().add("home-layout-card");

        VBox detailsLayout2 = new VBox(10, universityLabel, addressLabel, orgAddressLabel);
        detailsLayout2.setPadding(new Insets(20));
        detailsLayout2.setAlignment(Pos.CENTER_LEFT);
        detailsLayout2.getStyleClass().add("home-layout-card2");
        // make it to take 

        VBox detailsLayout3 = new VBox(10, curruntHostel, roomNo, hostelAddress);
        detailsLayout3.setPadding(new Insets(20));
        detailsLayout3.setAlignment(Pos.CENTER_LEFT);
        detailsLayout3.getStyleClass().add("home-layout-card3");

        HBox detailsSecondryLayout = new HBox(detailsLayout2, detailsLayout3);
        detailsSecondryLayout.setAlignment(Pos.CENTER);
        detailsSecondryLayout.setSpacing(20);


        VBox homeLayout = new VBox(detailsLayout, detailsSecondryLayout);
        homeLayout.setAlignment(Pos.CENTER);
        homeLayout.setSpacing(20);
        homeLayout.setPadding(new Insets(20));
        homeLayout.setStyle("-fx-background-color: #E3F2FD;");


        // Add responsive behavior
        ScrollPane scrollPane = new ScrollPane(homeLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #E3F2FD;");

        VBox container = new VBox(scrollPane);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.TOP_LEFT);
        container.getStyleClass().add("container");
        return container;
    }

    private Button createButtonWithIcon(String iconPath, String text) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
        icon.setFitWidth(30);
        icon.setFitHeight(30);
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

    public void setStudentController(StudentController controller) {
        this.controller = controller;
        System.out.println("Student Controller Set");
        homeTab.fire();
    }
}
