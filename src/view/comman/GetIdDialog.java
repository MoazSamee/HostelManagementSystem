package view.comman;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class GetIdDialog {

    private String id = null;

    public void start(Stage parentStage) {
        Stage dialogStage = new Stage();
        dialogStage.initOwner(parentStage);
        dialogStage.setTitle("Add ID");
        // set size
        dialogStage.setWidth(300);
        dialogStage.setHeight(200);

        // Create UI components
        TextField idField = new TextField();
        idField.setPromptText("Enter ID");

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        // Set up layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(idField, submitButton, cancelButton);
        layout.setPadding(new javafx.geometry.Insets(10));
        // set alignment
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Handle button actions
        submitButton.setOnAction(event -> {
            id = idField.getText();
            dialogStage.close();
        });

        cancelButton.setOnAction(event -> {
            id = null;  // No ID is collected if canceled
            dialogStage.close();
        });

        // Load the CSS file (optional, like the previous one)
        Scene scene = new Scene(layout, 300, 150);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // Set up the scene and show the dialog
        dialogStage.setScene(scene);
        dialogStage.showAndWait();  // Wait for the dialog to close
    }

    public Optional<String> getId() {
        return Optional.ofNullable(id);
    }
}
