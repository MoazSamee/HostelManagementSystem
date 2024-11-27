package view.Administrator;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AddNewRoomPage {

    private List<String> data = null;

    @SuppressWarnings("unused")
    public void start(Stage parentStage) {
        Stage dialogStage = new Stage();
        dialogStage.initOwner(parentStage);
        dialogStage.setTitle("Add New Room");

        // Set size
        dialogStage.setWidth(300);
        dialogStage.setHeight(300);

        // Create UI components
        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("Room Number");
        roomNumberField.setTextFormatter(new TextFormatter<>(c -> 
            (c.getControlNewText().matches("\\d*")) ? c : null));

        ComboBox<Integer> maxBedsComboBox = new ComboBox<>();
        maxBedsComboBox.setPromptText("Max Beds");
        maxBedsComboBox.getItems().addAll(1, 2, 3, 4, 5, 6);

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        // Set up layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(roomNumberField, maxBedsComboBox, submitButton, cancelButton);
        layout.setPadding(new javafx.geometry.Insets(10));
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Handle button actions
        submitButton.setOnAction(event -> {
            data = new ArrayList<>();
            data.add(roomNumberField.getText());
            data.add(maxBedsComboBox.getValue() != null ? maxBedsComboBox.getValue().toString() : ""); // Ensure value is not null
            dialogStage.close();
        });

        cancelButton.setOnAction(event -> {
            data = null; // No data is collected if canceled
            dialogStage.close();
        });

        // Load the CSS file
        Scene scene = new Scene(layout, 300, 300);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // Set up the scene and show the dialog
        dialogStage.setScene(scene);
        dialogStage.showAndWait(); // Wait for the dialog to close
    }

    public List<String> getData() {
        return data;
    }
}
