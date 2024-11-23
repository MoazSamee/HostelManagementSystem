package view.comman;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.Cursor;

public class HostelCardWide extends VBox {
    private static final int CARD_HEIGHT = 500;
    private static final int CARD_MIN_HEIGHT = 320;
    private Button deleteButton;

    @SuppressWarnings("unused")
    public HostelCardWide(String hostelId, String hostelName, int numberPendingApplications, 
                          String[] students, String[] maintenanceStaff) {
        super(10);
        setPadding(new Insets(20));
        setMinHeight(CARD_MIN_HEIGHT);
        setMaxHeight(CARD_HEIGHT);
        setCursor(Cursor.HAND);

        getStyleClass().add("hostel-card-wide");

        Label nameLabel = new Label(hostelName);
        nameLabel.setFont(Font.font("Arial", 18));
        nameLabel.getStyleClass().add("hostel-card-name");

        Label idLabel = new Label("ID: " + hostelId);
        idLabel.getStyleClass().add("hostel-card-id");

        Label pendingApplicationsLabel = new Label("Pending Applications: " + numberPendingApplications);
        pendingApplicationsLabel.getStyleClass().add("hostel-card-pending-applications");

        HBox listsContainer = new HBox(20);
        listsContainer.setPadding(new Insets(10, 0, 10, 0));
        listsContainer.setFillHeight(true);
        listsContainer.getStyleClass().add("hostel-card-lists");

        VBox studentsList = new VBox(5);
        studentsList.getStyleClass().add("hostel-card-list");
        Label studentsLabel = new Label("Students:");
        studentsList.getChildren().add(studentsLabel);
        for (String student : students) {
            studentsList.getChildren().add(new Label(student));
        }

        VBox staffList = new VBox(5);
        staffList.getStyleClass().add("hostel-card-list");
        Label staffLabel = new Label("Maintenance Staff:");
        staffList.getChildren().add(staffLabel);
        for (String staff : maintenanceStaff) {
            staffList.getChildren().add(new Label(staff));
        }

        ScrollPane studentsScroll = new ScrollPane(studentsList);
        studentsScroll.getStyleClass().add("hostel-card-list");
        studentsScroll.setFitToWidth(true);
        HBox.setHgrow(studentsScroll, Priority.ALWAYS);
        VBox.setVgrow(studentsScroll, Priority.ALWAYS);

        ScrollPane staffScroll = new ScrollPane(staffList);
        staffScroll.getStyleClass().add("hostel-card-list");
        staffScroll.setFitToWidth(true);
        HBox.setHgrow(staffScroll, Priority.ALWAYS);
        VBox.setVgrow(staffScroll, Priority.ALWAYS);

        listsContainer.getChildren().addAll(studentsScroll, staffScroll);

        deleteButton = new Button("❌");
        deleteButton.getStyleClass().add("disapprove-button");
        deleteButton.setOnAction(e -> {
            // Action for delete button
        });


        getChildren().addAll(nameLabel, idLabel, pendingApplicationsLabel, listsContainer, deleteButton);
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
}
