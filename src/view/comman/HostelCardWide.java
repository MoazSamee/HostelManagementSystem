package view.comman;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.Cursor;

public class HostelCardWide extends VBox {
    private static final int CARD_HEIGHT = 500;
    private static final int CARD_MIN_HEIGHT = 320;
    private Button deleteButton;
    private Button addStaffButton;
    private Button removeStaffButton;
    private Button addRoomButton;

    @SuppressWarnings("unused")
    public HostelCardWide(String hostelId, String hostelName, int numberPendingApplications, 
                          String[] students, String[] maintenanceStaff, String[] rooms) {
        super(10);
        setPadding(new Insets(20));
        setMinHeight(CARD_MIN_HEIGHT);
        setMaxHeight(CARD_HEIGHT);
        setCursor(Cursor.HAND);

        getStyleClass().add("hostel-card-wide");

        Label nameLabel = new Label(hostelName);
        nameLabel.setFont(Font.font("Arial", 18));
        nameLabel.getStyleClass().add("hostel-card-name");

        Label idLabel = new Label("🌍: " + hostelId);
        idLabel.getStyleClass().add("hostel-card-id");

        Label pendingApplicationsLabel = new Label("Pending Applications: " + numberPendingApplications);
        pendingApplicationsLabel.getStyleClass().add("hostel-card-pending-applications");

        HBox listsContainer = new HBox(20);
        listsContainer.setPadding(new Insets(10, 0, 10, 0));
        listsContainer.setFillHeight(true);
        listsContainer.getStyleClass().add("hostel-card-list");

        VBox studentsList = new VBox(5);
        studentsList.getStyleClass().add("flat-box");
        Label studentsLabel = new Label("Students");
        studentsLabel.setAlignment(Pos.CENTER);
        studentsLabel.setStyle("-fx-font-weight: bold;");
        studentsList.getChildren().add(studentsLabel);
        for (String student : students) {
            studentsList.getChildren().add(new Label(student));
        }
        VBox.setVgrow(studentsList, Priority.ALWAYS);

        VBox staffList = new VBox(5);
        staffList.getStyleClass().add("flat-box");
        Label staffLabel = new Label("Maintenance Staff");
        staffLabel.setAlignment(Pos.CENTER);
        staffLabel.setStyle("-fx-font-weight: bold;");

        staffList.getChildren().add(staffLabel);
        for (String staff : maintenanceStaff) {
            staffList.getChildren().add(new Label(staff));
        }
        VBox roomsList = new VBox(5);
        roomsList.getStyleClass().add("flat-box");
        Label roomsLabel = new Label("Rooms");
        roomsLabel.setAlignment(Pos.CENTER);
        roomsLabel.setStyle("-fx-font-weight: bold;");
        roomsList.getChildren().add(roomsLabel);
        for (String room : rooms) {
            roomsList.getChildren().add(new Label(room));
        }

        ScrollPane studentsScroll = new ScrollPane(studentsList);
        studentsScroll.getStyleClass().add("flat-box");
        studentsScroll.setFitToWidth(true);
        HBox.setHgrow(studentsScroll, Priority.ALWAYS);
        VBox.setVgrow(studentsScroll, Priority.ALWAYS);

        ScrollPane staffScroll = new ScrollPane(staffList);
        staffScroll.getStyleClass().add("flat-box");
        staffScroll.setFitToWidth(true);
        HBox.setHgrow(staffScroll, Priority.ALWAYS);
        VBox.setVgrow(staffScroll, Priority.ALWAYS);

        ScrollPane roomsScroll = new ScrollPane(roomsList);
        roomsScroll.getStyleClass().add("flat-box");
        // roomsScroll.setFitToWidth(true);
        // HBox.setHgrow(roomsScroll, Priority.ALWAYS);
        roomsScroll.setPrefWidth(180);
        VBox.setVgrow(roomsScroll, Priority.ALWAYS);

        listsContainer.getChildren().addAll(studentsScroll, staffScroll, roomsScroll);

        deleteButton = new Button("❌ Delete hostel");
        deleteButton.getStyleClass().add("disapprove-button");
        deleteButton.setOnAction(e -> {
            // Action for delete button
        });

        addStaffButton = new Button("➕ Staff");
        addStaffButton.getStyleClass().add("approve-button");
        addStaffButton.setOnAction(e -> {
            // Action for add staff button
        });

        removeStaffButton = new Button("➖ Staff");
        removeStaffButton.getStyleClass().add("disapprove-button");
        removeStaffButton.setOnAction(e -> {
            // Action for remove staff button
        });

        addRoomButton = new Button("➕ Room");
        addRoomButton.getStyleClass().add("approve-button");
        addRoomButton.setOnAction(e -> {
            // Action for add room button
        });


        HBox buttonsContainer = new HBox(10);
        // add empty spce to push buttons to right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        buttonsContainer.getChildren().addAll(deleteButton, spacer, addStaffButton, removeStaffButton, addRoomButton);


        getChildren().addAll(nameLabel, idLabel, pendingApplicationsLabel, listsContainer, buttonsContainer);
        // alogn last item to right

        addHoverEffects();
    }

    @SuppressWarnings("unused")
    private void addHoverEffects() {
        setOnMouseEntered(e -> getStyleClass().add("hostel-card-hover-wide"));
        setOnMouseExited(e -> getStyleClass().remove("hostel-card-hover-wide"));
    }

    public ButtonBase getDeleteButton() {
        return deleteButton;
    }

    public ButtonBase getAddStaffButton() {
        return addStaffButton;
    }

    public ButtonBase getRemoveStaffButton() {
        return removeStaffButton;
    }

    public ButtonBase getAddRoomButton() {
        return addRoomButton;
    }
}
