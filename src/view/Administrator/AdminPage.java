package view.Administrator;

import java.util.List;

import controller.Administrator.AdminController;
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
import view.comman.HostelCardWide;

public class AdminPage extends Application {

    private boolean isSidebarExpanded = true;
    AdminController controller;
    private Button homeTab;
    private Button roomRequestsTab;
    private Button studentsTab;
    private Button hostelsTab;
    private Button compliantTab;
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
        toggleButton.setAlignment(Pos.CENTER);
        homeTab = createButtonWithIcon("/resources/home.png", " Home");
        roomRequestsTab = createButtonWithIcon("/resources/bell.png", " Room Requests");
        studentsTab = createButtonWithIcon("/resources/user.png", " Students");
        hostelsTab = createButtonWithIcon("/resources/pin.png", " Hostels");
        compliantTab = createButtonWithIcon("/resources/forbidden.png", " Complaints");
        settingsTab = createButtonWithIcon("/resources/setting.png", " Settings");

        homeTab.setPrefWidth(250);
        roomRequestsTab.setPrefWidth(250);
        studentsTab.setPrefWidth(250);
        hostelsTab.setPrefWidth(250);
        compliantTab.setPrefWidth(250);
        settingsTab.setPrefWidth(250);

        updateButtonIconsWithText(homeTab, roomRequestsTab, studentsTab, hostelsTab, compliantTab, settingsTab);

        sidebar.getChildren().addAll(toggleButton, homeTab, roomRequestsTab, studentsTab, hostelsTab,compliantTab, settingsTab);
        // sidebar.setMaxWidth(250);

        // Content Area
        BorderPane contentPane = new BorderPane();
        contentPane.setPadding(new Insets(15));

        Text contentText = new Text("Welcome to the Admin Dashboard");

        // Sidebar Navigation Events
        homeTab.setOnAction(e -> {
            setActiveTab(homeTab, roomRequestsTab, studentsTab, hostelsTab,compliantTab, settingsTab);
            VBox homePage = createHomePage();
            contentPane.setCenter(homePage);
        });

        roomRequestsTab.setOnAction(e -> {
            setActiveTab(roomRequestsTab, homeTab, studentsTab, hostelsTab,compliantTab, settingsTab);
            VBox roomRequestsPage = createRoomRequestsPage();
            contentPane.setCenter(roomRequestsPage);
        });

        studentsTab.setOnAction(e -> {
            setActiveTab(studentsTab, homeTab, roomRequestsTab, hostelsTab,compliantTab, settingsTab);
            VBox studentsPage = createViewStudentPage();
            contentPane.setCenter(studentsPage);
        });

        hostelsTab.setOnAction(e -> {
            setActiveTab(hostelsTab, homeTab, roomRequestsTab, studentsTab,compliantTab, settingsTab);
            VBox hostelsPage = createHostelsPage();
            contentPane.setCenter(hostelsPage);
        });

        compliantTab.setOnAction(e -> {
            setActiveTab(compliantTab, homeTab, roomRequestsTab, studentsTab, hostelsTab, settingsTab);
            VBox compliantPage = createCompliantPage();
            contentPane.setCenter(compliantPage);
        });

        settingsTab.setOnAction(e -> {
            setActiveTab(settingsTab, homeTab, roomRequestsTab, studentsTab, hostelsTab, compliantTab);
            VBox settingsPage = createSettingsPage();
            contentPane.setCenter(settingsPage);
        });

        // Toggle Sidebar Expand/Collapse
        toggleButton.setOnAction(e -> {
            if (isSidebarExpanded) {
                sidebar.setPrefWidth(50);
                for (Button button : new Button[] { homeTab, roomRequestsTab, studentsTab, hostelsTab, compliantTab, settingsTab }) {
                    button.setMinWidth(58);
                    button.setMaxWidth(58);
                }
                updateButtonIconsOnly(homeTab, roomRequestsTab, studentsTab, hostelsTab, compliantTab, settingsTab);
                toggleButton.setText("☰");
            } else {
                sidebar.setPrefWidth(200);
                for (Button button : new Button[] { homeTab, roomRequestsTab, studentsTab, hostelsTab, compliantTab, settingsTab }) {
                    button.setMinWidth(200);
                    button.setMaxWidth(Double.MAX_VALUE);
                }
                updateButtonIconsWithText(homeTab, roomRequestsTab, studentsTab, hostelsTab, compliantTab, settingsTab);
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
                { "apps.png", " Home" },
                { "requests.png", " Room Requests" },
                { "students.png", " Students" },
                { "hostels.png", " Hostels" },
                { "complaints.png", " Complaints" },
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

    public static void main(String[] args) {
        launch(args);
    }

    ///////////////////////////////////////////////////////////////////////////////
    /// Helper Methods /////
    ///////////////////////////////////////////////////////////////////////////////

    private GridPane createHeader(String[] headers) {
        GridPane header = new GridPane();
        header.getStyleClass().add("grid-pane");
        header.setHgap(10);
        header.setPadding(new Insets(5));

        for (int i = 0; i < headers.length; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(20);
            header.getColumnConstraints().add(column);
        }

        for (int i = 0; i < headers.length; i++) {
            Label headerLabel = new Label(headers[i]);
            headerLabel.getStyleClass().add("header-cell");
            header.add(headerLabel, i, 0);
        }
        return header;
    }

    ///////////////////////////////////////////////////////////////////////////////
    /// Room Requests Page /////
    ///////////////////////////////////////////////////////////////////////////////

    public VBox createRoomRequestsPage() {
        var roomRequests = controller.fetchRoomRequests();

        VBox roomRequestsPage = new VBox(10);
        roomRequestsPage.setPadding(new Insets(15));
        roomRequestsPage.setAlignment(Pos.TOP_CENTER);
        roomRequestsPage.getStyleClass().add("list-page");

        // Header
        String[] headers = { "Name", "ID", "Hostel", "Room", "Actions" };
        GridPane header = createHeader(headers);
        roomRequestsPage.getChildren().add(header);

        // Add rows for each request
        for (String[] request : roomRequests) {
            addRequestRow(roomRequestsPage, request);
        }

        // Wrap the VBox in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(roomRequestsPage);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setPadding(new Insets(10));

        VBox container = new VBox(scrollPane);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.TOP_LEFT);
        container.getStyleClass().add("container");
        return container;
    }

    @SuppressWarnings("unused")
    private void addRequestRow(VBox roomRequestsPage, String[] request) {
        GridPane requestRow = new GridPane();
        requestRow.getStyleClass().add("request-row");
        requestRow.setHgap(10);
        requestRow.setPadding(new Insets(5));

        for (int i = 0; i < 5; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(20);
            requestRow.getColumnConstraints().add(column);
        }

        Label nameLabel = new Label(request[0]);
        nameLabel.getStyleClass().add("data-cell");

        Label idLabel = new Label(request[1]);
        idLabel.getStyleClass().add("data-cell");

        Label hostelLabel = new Label(request[2]);
        hostelLabel.getStyleClass().add("data-cell");

        Label roomLabel = new Label(request[3]);
        roomLabel.getStyleClass().add("data-cell");

        Button approveButton = new Button("✅");
        approveButton.getStyleClass().add("approve-button");

        Button disapproveButton = new Button("❌");
        disapproveButton.getStyleClass().add("disapprove-button");

        approveButton.setOnAction(ev -> {
            controller.approveRequest(request[4]);
            refreshRoomRequestsPage(roomRequestsPage);
            removeRequestRow(roomRequestsPage, requestRow);
        });

        disapproveButton.setOnAction(ev -> {
            controller.disapproveRequest(request[4]);
            refreshRoomRequestsPage(roomRequestsPage);
            removeRequestRow(roomRequestsPage, requestRow);
        });

        requestRow.add(nameLabel, 0, 0);
        requestRow.add(idLabel, 1, 0);
        requestRow.add(hostelLabel, 2, 0);
        requestRow.add(roomLabel, 3, 0);

        HBox actions = new HBox(10, approveButton, disapproveButton);
        actions.getStyleClass().add("action-cell");
        actions.setAlignment(Pos.CENTER_RIGHT);
        requestRow.add(actions, 4, 0);

        roomRequestsPage.getChildren().add(requestRow);
    }

    private void removeRequestRow(VBox roomRequestsPage, GridPane requestRow) {
        roomRequestsPage.getChildren().remove(requestRow);
    }

    private void refreshRoomRequestsPage(VBox roomRequestsPage) {
        roomRequestsPage.getChildren().clear();
        String[] headers = { "Name", "ID", "Hostel", "Room", "Actions" };
        GridPane header = createHeader(headers);
        roomRequestsPage.getChildren().add(header);

        for (String[] request : controller.fetchRoomRequests()) {
            addRequestRow(roomRequestsPage, request);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    /// View Students Page /////
    ///////////////////////////////////////////////////////////////////////////////

    public VBox createViewStudentPage() {
        var students = controller.fetchStudents();

        VBox studentPage = new VBox(10);
        studentPage.setPadding(new Insets(15));
        studentPage.setAlignment(Pos.TOP_CENTER);
        studentPage.getStyleClass().add("list-page");

        // Header
        String[] headers = { "ID", "Name", "Email", "Phone", "Address", "University/Job", "Actions" };
        GridPane header = createHeader(headers);
        studentPage.getChildren().add(header);

        // Add rows for each student
        for (String[] student : students) {
            addStudentRow(studentPage, student);
        }

        // Wrap the VBox in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(studentPage);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setPadding(new Insets(10));

        VBox container = new VBox(scrollPane);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.TOP_LEFT);
        container.getStyleClass().add("container");
        return container;
    }

    @SuppressWarnings("unused")
    private void addStudentRow(VBox studentPage, String[] student) {
        GridPane studentRow = new GridPane();
        studentRow.getStyleClass().add("student-row");
        studentRow.setHgap(10);
        studentRow.setPadding(new Insets(5));

        // Adding columns
        for (int i = 0; i < 7; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(14.28);
            studentRow.getColumnConstraints().add(column);
        }

        // Creating Labels for student details
        Label idLabel = new Label(student[0]);
        idLabel.getStyleClass().add("data-cell");

        Label nameLabel = new Label(student[1]);
        nameLabel.getStyleClass().add("data-cell");

        Label emailLabel = new Label(student[2]);
        emailLabel.getStyleClass().add("data-cell");

        Label phoneLabel = new Label(student[3]);
        phoneLabel.getStyleClass().add("data-cell");

        Label addressLabel = new Label(student[4]);
        addressLabel.getStyleClass().add("data-cell");

        Label universityOrJobLabel = new Label(student[5]);
        universityOrJobLabel.getStyleClass().add("data-cell");

        // Remove Button
        Button removeButton = new Button("❌");
        removeButton.getStyleClass().add("disapprove-button");

        removeButton.setOnAction(ev -> {
            controller.removeStudent(student[0]);
            removeStudentRow(studentPage, studentRow);
            refreshStudentPage(studentPage);
        });

        // Adding all Labels and the Remove Button to the row
        studentRow.add(idLabel, 0, 0);
        studentRow.add(nameLabel, 1, 0);
        studentRow.add(emailLabel, 2, 0);
        studentRow.add(phoneLabel, 3, 0);
        studentRow.add(addressLabel, 4, 0);
        studentRow.add(universityOrJobLabel, 5, 0);

        // Actions column with remove button
        HBox actions = new HBox(10, removeButton);
        actions.getStyleClass().add("action-cell");
        actions.setAlignment(Pos.CENTER_RIGHT);
        studentRow.add(actions, 6, 0);

        studentPage.getChildren().add(studentRow);
    }

    private void removeStudentRow(VBox studentPage, GridPane studentRow) {
        studentPage.getChildren().remove(studentRow);
    }

    private void refreshStudentPage(VBox studentPage) {
        studentPage.getChildren().clear();
        String[] headers = { "ID", "Name", "Email", "Phone", "Address", "University/Job", "Actions" };
        GridPane header = createHeader(headers);
        studentPage.getChildren().add(header);

        for (String[] student : controller.fetchStudents()) {
            addStudentRow(studentPage, student);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    /// Hostels Page /////
    ///////////////////////////////////////////////////////////////////////////////

    public VBox createHostelsPage() {
        VBox hostelPage = new VBox(10);
        hostelPage.getStyleClass().add("list-page");
        refreshHostelsPage(hostelPage);

        ScrollPane scrollPane = new ScrollPane(hostelPage);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setPadding(new Insets(10));

        VBox container = new VBox(scrollPane);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.TOP_LEFT);
        container.getStyleClass().add("container");

        return container;
    }

    @SuppressWarnings("unused")
    private void refreshHostelsPage(VBox hostelPage) {
        List<String[]> hostels = controller.fetchHostels();

        hostelPage.getChildren().clear();
        String[] headers = { "Hostels" };
        GridPane header = createHeader(headers);
        hostelPage.getChildren().add(header);

        for (String[] hostel : hostels) {
            addHostelCard(hostelPage, hostel);
        }

        Button addHostelButton = new Button("+");
        addHostelButton.setOnAction(e -> {
            controller.addHostel();
        });
        addHostelButton.setAlignment(Pos.CENTER);
        hostelPage.getChildren().add(addHostelButton);
        hostelPage.setAlignment(Pos.TOP_CENTER);
    }

    @SuppressWarnings("unused")
    private void addHostelCard(VBox hostelPage, String[] hostel) {
        String location = hostel[0];
        String hostelName = hostel[1];
        int numberPendingApplications = Integer.parseInt(hostel[2]);
        String[] students = hostel[3].split(",");
        String[] maintenanceStaff = hostel[4].split(",");

        HostelCardWide hostelCard = new HostelCardWide(location, hostelName, numberPendingApplications, students,
                maintenanceStaff);
        // hostelCard deleteButton event handler
        hostelCard.getDeleteButton().setOnAction(e -> {
            controller.removeHostel(hostel[5]);
            refreshHostelsPage(hostelPage);
        });

        hostelCard.getAddStaffButton().setOnAction(e -> {
            controller.addStaffToHostel(hostel[5]);
            refreshHostelsPage(hostelPage);

        });

        hostelCard.getRemoveStaffButton().setOnAction(e -> {
            controller.removeStaffFromHostel(hostel[5]);
            refreshHostelsPage(hostelPage);
        });

        hostelPage.getChildren().add(hostelCard);
    }

    ///////////////////////////////////////////////////////////////////////////////
    /// Settings Page /////
    ///////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unused")
    public VBox createSettingsPage() {

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

    /////////////////////////////////////////////////////////////////////////////
    /// Home Page /////
    /// /////////////////////////////////////////////////////////////////////////
    
    public VBox createHomePage()
    {
        // Create labels for student details
        Label nameLabel = new Label("Welcome " + controller.getUserName()+ "! 😊");
        nameLabel.setStyle("-fx-font: 36 arial; -fx-font-weight: bold; -fx-text-fill: white;");
        Label emailLabel = new Label("📧 "+controller.getUserEmail());
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

    /////////////////////////////////////////////////////////////////////////////
    /// Other Methods /////
    /// /////////////////////////////////////////////////////////////////////////
    
    public void setAdminController(AdminController adminController) {
        this.controller = adminController;
        this.homeTab.fire();
    }

    /////////////////////////////////////////////////////////////////////////////
    /// Compliant Page /////
    /// /////////////////////////////////////////////////////////////////////////
    
    public VBox createCompliantPage()
    {
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
            addComplaintRow(requestPage, request);
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
    private void addComplaintRow(VBox requestPage, String[] request) {
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
                compliantTab.fire();
            });
        }
        else if (request[3].equals("In Progress")) {
            resolveButton = new Button("⌛ Resolve");
            // resolveButton.getStyleClass().add("aproove-button");
            resolveButton.setOnAction(e -> {
                controller.handleResolve(request);
                compliantTab.fire();
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

}
