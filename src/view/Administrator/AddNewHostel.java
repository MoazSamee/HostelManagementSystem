package view.Administrator;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AddNewHostel {

    private List<String> data = null;

    @SuppressWarnings("unused")
    public void start(Stage parentStage) {
        Stage dialogStage = new Stage();
        dialogStage.initOwner(parentStage);
        dialogStage.setTitle("Add New Hostel");

        // Set size
        dialogStage.setWidth(300);
        dialogStage.setHeight(400);

        // Create UI components
        TextField idField = new TextField();
        idField.setPromptText("Hostel ID");

        TextField nameField = new TextField();
        nameField.setPromptText("Hostel Name");

        TextField locationField = new TextField();
        locationField.setPromptText("Hostel Location");

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        // Set up layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(idField, nameField, locationField, submitButton, cancelButton);
        layout.setPadding(new javafx.geometry.Insets(10));
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Handle button actions
        submitButton.setOnAction(event -> {
            data = new ArrayList<>();
            data.add(idField.getText());
            data.add(nameField.getText());
            data.add(locationField.getText());
            dialogStage.close();
        });

        cancelButton.setOnAction(event -> {
            data = null;  // No data is collected if canceled
            dialogStage.close();
        });

        // Load the CSS file
        Scene scene = new Scene(layout, 300, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // Set up the scene and show the dialog
        dialogStage.setScene(scene);
        dialogStage.showAndWait();  // Wait for the dialog to close
    }

    public List<String> getData() {
        return data;
    }
}
