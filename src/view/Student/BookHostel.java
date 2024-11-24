package view.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Hostel.Hostel;
import model.Hostel.Room;

public class BookHostel {

    private final Hostel hostel;
    private Room selectedRoom;


    // Constructor
    public BookHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    @SuppressWarnings({ "unchecked", "unused" })
    public void start(Stage parentStage) {
        Stage dialogStage = new Stage();
        dialogStage.initOwner(parentStage);
        dialogStage.setTitle("Book Hostel");

        // Set size
        dialogStage.setWidth(500);
        dialogStage.setHeight(600);

        // Labels for hostel details
        Label hostelIdLabel = new Label("Hostel ID: " + hostel.getHostelId());
        Label hostelNameLabel = new Label("Hostel Name: " + hostel.getHostelName());
        Label hostelLocationLabel = new Label("Location: " + hostel.getHostelLocation());

        // TableView for rooms
        TableView<Room> roomsTable = new TableView<>();
        ObservableList<Room> rooms = FXCollections.observableArrayList(hostel.getAvailableRooms());
        roomsTable.setItems(rooms);

        // Define columns
        TableColumn<Room, String> roomIdCol = new TableColumn<>("Room ID");
        roomIdCol.setCellValueFactory(new PropertyValueFactory<>("roomId"));

        TableColumn<Room, Integer> maxBedsCol = new TableColumn<>("Max Beds");
        maxBedsCol.setCellValueFactory(new PropertyValueFactory<>("maxBeds"));

        TableColumn<Room, Integer> freeSpaceCol = new TableColumn<>("Free Space");
        freeSpaceCol.setCellValueFactory(new PropertyValueFactory<>("freeSpace"));

        TableColumn<Room, String> roomNoCol = new TableColumn<>("Room No");
        roomNoCol.setCellValueFactory(new PropertyValueFactory<>("roomNo"));

        roomsTable.getColumns().addAll(roomIdCol, maxBedsCol, freeSpaceCol, roomNoCol);

        // Book Button
        Button bookButton = new Button("Book");
        bookButton.setOnAction(event -> {
            // print selected row
            selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {
                dialogStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("No room selected");
                alert.setContentText("Please select a room to book.");
                alert.showAndWait();
            }
        });

        // Cancel Button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            selectedRoom = null;
            dialogStage.close();
        });

        // Set up layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                hostelIdLabel,
                hostelNameLabel,
                hostelLocationLabel,
                roomsTable,
                bookButton,
                cancelButton
        );
        layout.setPadding(new javafx.geometry.Insets(10));
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Load the CSS file
        Scene scene = new Scene(layout, 500, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // Set up the scene and show the dialog
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    public Room getSelectedRoom() {
        return selectedRoom;
    }

}
