package view.MaintenanceStaff;

import java.util.List;

import controller.MaintenanceStaff.MaintenanceStaffController;
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

public class MaintenanceStaffView extends Application {

    private boolean isSidebarExpanded = true;
    MaintenanceStaffController controller;
    private Button homeTab;
    private Button roomMaintainancerequestTab;
    // private Button studentsTab;
    // private Button hostelsTab;
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
        homeTab = createButtonWithIcon("/resources/home.png", " Home");
        roomMaintainancerequestTab = createButtonWithIcon("/resources/bell.png", " Maintainance Requests");
        settingsTab = createButtonWithIcon("/resources/setting.png", " Settings");

        homeTab.setPrefWidth(200);
        roomMaintainancerequestTab.setPrefWidth(200);
        settingsTab.setPrefWidth(200);

        updateButtonIconsWithText(homeTab, roomMaintainancerequestTab, settingsTab);

        sidebar.getChildren().addAll(toggleButton, homeTab, roomMaintainancerequestTab, settingsTab);
        sidebar.setMaxWidth(200);

        // Content Area
        BorderPane contentPane = new BorderPane();
        contentPane.setPadding(new Insets(15));

        Text contentText = new Text("Welcome to the Staff Dashboard");

        // Sidebar Navigation Events
        homeTab.setOnAction(e -> {
            setActiveTab(homeTab, roomMaintainancerequestTab, settingsTab);
            VBox homePage = createHomePage();
            contentPane.setCenter(homePage);
        });

        roomMaintainancerequestTab.setOnAction(e -> {
            setActiveTab(roomMaintainancerequestTab, homeTab, settingsTab);
            VBox roomRequestsPage = createMaintainanceRequestPage();
            contentPane.setCenter(roomRequestsPage);
        });

        settingsTab.setOnAction(e -> {
            setActiveTab(settingsTab, homeTab, roomMaintainancerequestTab);
            VBox settingsPage = createSettingsPage();
            contentPane.setCenter(settingsPage);
        });

        // Toggle Sidebar Expand/Collapse
        toggleButton.setOnAction(e -> {
            if (isSidebarExpanded) {
                sidebar.setPrefWidth(50);
                for (Button button : new Button[] { homeTab, roomMaintainancerequestTab, settingsTab }) {
                    button.setMinWidth(50);
                    button.setMaxWidth(50);
                }
                updateButtonIconsOnly(homeTab, roomMaintainancerequestTab, settingsTab);
                toggleButton.setText("☰");
            } else {
                sidebar.setPrefWidth(200);
                for (Button button : new Button[] { homeTab, roomMaintainancerequestTab, settingsTab }) {
                    button.setMinWidth(200);
                    button.setMaxWidth(Double.MAX_VALUE);
                }
                updateButtonIconsWithText(homeTab, roomMaintainancerequestTab, settingsTab);
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

        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize with the home page
        if (controller != null) {
            homeTab.fire();
        }
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
                { "apps.png", " Home" },
                { "requests.png", " Room Requests" },
                { "students.png", " Students" },
                { "hostels.png", " Hostels" },
                { "settings.png", " Settings" }
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(buttonData[i][1]);
            buttons[i].setAlignment(Pos.CENTER_LEFT);
        }
    }

    private void setActiveTab(Button active, Button... others) {
        active.setStyle("-fx-background-color: white; -fx-text-fill: #2196F3;");
        for (Button button : others) {
            button.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        }
    }

        
    private VBox createHomePage()
    {
        // Create labels for student details
        Label nameLabel = new Label("Welcome " + controller.getUserName()+ "! 😊");
        nameLabel.setStyle("-fx-font: 36 arial; -fx-font-weight: bold; -fx-text-fill: white;");
        Label emailLabel = new Label("📧 "+controller.getEmail());
        emailLabel.setStyle("-fx-font: 24 arial; -fx-text-fill: white;");
        Label phoneLabel = new Label("📞 "+ controller.getUserPhone());
        phoneLabel.setStyle("-fx-font: 24 arial; -fx-text-fill: white;");

        // Create a VBox for the details and style it
        VBox detailsLayout = new VBox(10, nameLabel, emailLabel, phoneLabel);
        detailsLayout.setPadding(new Insets(20));
        detailsLayout.setAlignment(Pos.CENTER);
        detailsLayout.getStyleClass().add("home-layout-card");


        VBox homeLayout = new VBox(detailsLayout);
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

    @SuppressWarnings("unused")
    private VBox createSettingsPage() {

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
                                    phoneField.getText(), passwordField.getText(), passwordField2.getText()));
    

        HBox passwordGroup = new HBox(10, passwordField, passwordField2);
        passwordGroup.setAlignment(Pos.CENTER);
        HBox contactGroup = new HBox(10, emailField, phoneField);
        contactGroup.setAlignment(Pos.CENTER);

        // Layout adjustments
        VBox formLayout = new VBox(10, nameField, contactGroup,passwordGroup);
        // VBox formLayout = new VBox(10, nameLabel, nameField, emailLabel, emailField, phoneLabel, phoneField, passwordLabel, passwordField, passwordLabel2, passwordField2);
        formLayout.setSpacing(15);
        formLayout.setAlignment(Pos.CENTER);
    
        // Make the save button more visually prominent and align it
        HBox buttonLayout = new HBox(saveButton);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setSpacing(10);
    
        VBox content = new VBox(20, formLayout, buttonLayout);
        content.setAlignment(Pos.CENTER);
    

        return content;
    }

    public VBox createMaintainanceRequestPage() {
        List<String[]> requests = controller.fetchRequests();
    
        VBox requestPage = new VBox(10);
        requestPage.setPadding(new Insets(15));
        requestPage.setAlignment(Pos.TOP_CENTER);
        requestPage.getStyleClass().add("flat-box");
    
        // Header
        Label title = new Label("Requests");
        title.getStyleClass().add("page-title");
        requestPage.getChildren().add(title);
    
        // Add rows for each request
        for (String[] request : requests) {
            addRequestRow(requestPage, request);
        }
    
        // Wrap the VBox in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(requestPage);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setPadding(new Insets(10));
    
        VBox container = new VBox(scrollPane);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.TOP_LEFT);
        container.getStyleClass().add("scroll-pane");
        return container;
    }
    
    @SuppressWarnings("unused")
    private void addRequestRow(VBox requestPage, String[] request) {
        HBox requestHeader = new HBox(10);
        requestHeader.setAlignment(Pos.CENTER_LEFT);
        requestHeader.setPadding(new Insets(10));
        requestHeader.getStyleClass().add("header-cell");
    
        // Request details: User name, room number, hostel name, hostel address
        Label userName = new Label("Room (" + request[0] + ")");
        Label roomNumber = new Label("Hostel: " + request[1]);
        Label hostelName = new Label("Address: " + request[2]);
        Label status = new Label("Status: " + request[3]);
    
        requestHeader.getChildren().addAll(userName, roomNumber, hostelName);
    
        // Request text (multi-line label)
        Label requestText = new Label(request[4]);
        requestText.setWrapText(true);
        requestText.getStyleClass().add("request-text");
        requestText.setPadding(new Insets(5));
    
        Button resolveButton = null;
        // Resolve button
        if (request[3].equals("Pending")) {
            resolveButton = new Button("🔔 Start Resolving");
            // resolveButton.getStyleClass().add("aproove-button");
            resolveButton.setOnAction(e -> {
                controller.handleResolve(request);
                roomMaintainancerequestTab.fire();
            });
        }
        else if (request[3].equals("In Progress")) {
            resolveButton = new Button("⌛ Resolve");
            // resolveButton.getStyleClass().add("aproove-button");
            resolveButton.setOnAction(e -> {
                controller.handleResolve(request);
                roomMaintainancerequestTab.fire();
            });
        }
        else {
            resolveButton = new Button("✅ Resolved");
            System.err.println("reuest sysys"+request[3]);
            // resolveButton.getStyleClass().add("aproove-button");
            resolveButton.setDisable(true);
        }


        HBox resolveButtonGroup = new HBox(resolveButton);
        resolveButtonGroup.setAlignment(Pos.TOP_RIGHT);

    
        VBox requestRow = new VBox(5);
        requestRow.setPadding(new Insets(10));
        requestRow.getStyleClass().add("request-row");
        requestRow.getChildren().addAll(requestHeader, requestText, resolveButtonGroup);
    
        requestPage.getChildren().add(requestRow);
    }

    public void setController(MaintenanceStaffController maintenanceStaffController) {
        this.controller = maintenanceStaffController;
    }

    public void setMaintenanceStaffController(MaintenanceStaffController maintenanceStaffController) {
        this.controller = maintenanceStaffController;
    }

}
