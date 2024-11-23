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
    AdminController controller = new AdminController();

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
        Button homeTab = createButtonWithIcon("/resources/home.png", " Home");
        Button roomRequestsTab = createButtonWithIcon("/resources/bell.png", " Room Requests");
        Button studentsTab = createButtonWithIcon("/resources/user.png", " Students");
        Button hostelsTab = createButtonWithIcon("/resources/pin.png", " Hostels");
        Button settingsTab = createButtonWithIcon("/resources/setting.png", " Settings");

        homeTab.setPrefWidth(200);
        roomRequestsTab.setPrefWidth(200);
        studentsTab.setPrefWidth(200);
        hostelsTab.setPrefWidth(200);
        settingsTab.setPrefWidth(200);

        updateButtonIconsWithText(homeTab, roomRequestsTab, studentsTab, hostelsTab, settingsTab);

        sidebar.getChildren().addAll(toggleButton, homeTab, roomRequestsTab, studentsTab, hostelsTab, settingsTab);
        sidebar.setMaxWidth(200);

        // Content Area
        BorderPane contentPane = new BorderPane();
        contentPane.setPadding(new Insets(15));

        Text contentText = new Text("Welcome to the Admin Dashboard");

        // Sidebar Navigation Events
        homeTab.setOnAction(e -> {
            setActiveTab(homeTab, roomRequestsTab, studentsTab, hostelsTab, settingsTab);
            VBox homePage = new VBox(new Text("Welcome to the Home Page"));
            homePage.setAlignment(Pos.CENTER);
            contentPane.setCenter(homePage);
        });

        roomRequestsTab.setOnAction(e -> {
            setActiveTab(roomRequestsTab, homeTab, studentsTab, hostelsTab, settingsTab);
            VBox roomRequestsPage = createRoomRequestsPage();
            contentPane.setCenter(roomRequestsPage);
        });

        studentsTab.setOnAction(e -> {
            setActiveTab(studentsTab, homeTab, roomRequestsTab, hostelsTab, settingsTab);
            VBox studentsPage = createViewStudentPage();
            contentPane.setCenter(studentsPage);
        });

        hostelsTab.setOnAction(e -> {
            setActiveTab(hostelsTab, homeTab, roomRequestsTab, studentsTab, settingsTab);
            VBox hostelsPage = createHostelsPage();
            contentPane.setCenter(hostelsPage);
        });

        settingsTab.setOnAction(e -> {
            setActiveTab(settingsTab, homeTab, roomRequestsTab, studentsTab, hostelsTab);
            VBox settingsPage = createSettingsPage();
            contentPane.setCenter(settingsPage);
        });

        // Toggle Sidebar Expand/Collapse
        toggleButton.setOnAction(e -> {
            if (isSidebarExpanded) {
                sidebar.setPrefWidth(50);
                for (Button button : new Button[] { homeTab, roomRequestsTab, studentsTab, hostelsTab, settingsTab }) {
                    button.setMinWidth(50);
                    button.setMaxWidth(50);
                }
                updateButtonIconsOnly(homeTab, roomRequestsTab, studentsTab, hostelsTab, settingsTab);
                toggleButton.setText("☰");
            } else {
                sidebar.setPrefWidth(200);
                for (Button button : new Button[] { homeTab, roomRequestsTab, studentsTab, hostelsTab, settingsTab }) {
                    button.setMinWidth(200);
                    button.setMaxWidth(Double.MAX_VALUE);
                }
                updateButtonIconsWithText(homeTab, roomRequestsTab, studentsTab, hostelsTab, settingsTab);
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

        primaryStage.setTitle("Admin Dashboard");
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
            controller.approveRequest(request[0]);
            refreshRoomRequestsPage(roomRequestsPage);
            removeRequestRow(roomRequestsPage, requestRow);
        });

        disapproveButton.setOnAction(ev -> {
            controller.disapproveRequest(request[0]);
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

    private void refreshHostelsPage(VBox hostelPage) {
        List<String[]> hostels = controller.fetchHostels();

        hostelPage.getChildren().clear();
        String[] headers = { "Hostels" };
        GridPane header = createHeader(headers);
        hostelPage.getChildren().add(header);

        for (String[] hostel : hostels) {
            addHostelCard(hostelPage, hostel);
        }
    }

    @SuppressWarnings("unused")
    private void addHostelCard(VBox hostelPage, String[] hostel) {
        String hostelId = hostel[0];
        String hostelName = hostel[1];
        int numberPendingApplications = Integer.parseInt(hostel[2]);
        String[] students = hostel[3].split(",");
        String[] maintenanceStaff = hostel[4].split(",");

        HostelCardWide hostelCard = new HostelCardWide(hostelId, hostelName, numberPendingApplications, students,
                maintenanceStaff);
        // hostelCard deleteButton event handler
        hostelCard.getDeleteButton().setOnAction(e -> {
            controller.removeHostel(hostelId);
            refreshHostelsPage(hostelPage);
        });

        hostelCard.getAddStaffButton().setOnAction(e -> {
            controller.addStaffToHostel(hostelId);
            refreshHostelsPage(hostelPage);

        });

        hostelCard.getRemoveStaffButton().setOnAction(e -> {
            controller.removeStaffFromHostel(hostelId);
            refreshHostelsPage(hostelPage);
        });

        hostelPage.getChildren().add(hostelCard);
    }

    ///////////////////////////////////////////////////////////////////////////////
    /// Settings Page /////
    ///////////////////////////////////////////////////////////////////////////////

    public VBox createSettingsPage() {

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
        saveButton.setOnAction(ev -> controller.editProfile(nameField.getText(), emailField.getText(),
                phoneField.getText(), passwordField.getText(), passwordField2.getText()));

        VBox formLayout = new VBox(10, nameLabel, nameField, emailLabel, emailField, phoneLabel, phoneField,
                passwordLabel, passwordField, passwordLabel2, passwordField2);
        formLayout.setSpacing(15);
        formLayout.setAlignment(Pos.CENTER);

        HBox buttonLayout = new HBox(saveButton);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setSpacing(10);

        VBox content = new VBox(20, formLayout, buttonLayout);
        content.setAlignment(Pos.CENTER);

        return content;
    }

}
