package view.comman;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.Cursor;

public class HostelCard extends VBox {
    private static final int CARD_WIDTH = 320;
    private static final int CARD_HEIGHT = 150;

    public HostelCard(String hostelName,String hostelLocation, int numberAvailableRooms, int numberOfStudents) {
        super(10);
        setPadding(new Insets(20));
        setPrefWidth(CARD_WIDTH);
        setPrefHeight(CARD_HEIGHT);
        setCursor(Cursor.HAND);

        // Add CSS class
        getStyleClass().add("hostel-card");

        Label nameLabel = new Label(hostelName);
        nameLabel.setFont(Font.font("Arial", 18));
        nameLabel.getStyleClass().add("hostel-card-name");

        Label idLabel = new Label("🌍 " + hostelLocation);
        // idLabel.setFont(Font.font("Arial", 14));
        idLabel.getStyleClass().add("hostel-card-id");

        Label roomsLabel = new Label("Available Rooms: " + numberAvailableRooms);
        // roomsLabel.setFont(Font.font("Arial", 14));
        roomsLabel.getStyleClass().add("hostel-card-rooms");

        Label studentsLabel = new Label("Students: " + numberOfStudents);
        // studentsLabel.setFont(Font.font("Arial", 14));
        studentsLabel.getStyleClass().add("hostel-card-students");

        getChildren().addAll(nameLabel, idLabel, roomsLabel, studentsLabel);

        addHoverEffects();
    }

    @SuppressWarnings("unused")
    private void addHoverEffects() {
        setOnMouseEntered(e -> getStyleClass().add("hostel-card-hover"));
        setOnMouseExited(e -> getStyleClass().remove("hostel-card-hover"));
    }

    public int getCardWidth() {
        return CARD_WIDTH;
    }
}
