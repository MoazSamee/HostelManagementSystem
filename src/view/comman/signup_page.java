package view.comman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class signup_page extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signup_page.fxml"));
        
        Scene scene = new Scene(loader.load());
        // scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hostel Ease");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
