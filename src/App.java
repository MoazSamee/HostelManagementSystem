import model.User.StudentModel;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        StudentModel student = new StudentModel("1", "John Doe", "", "", "1", 1, "", "", "");
        student.onMaintenanceRequestSubmitted("Complaint", "Details");
    }
}

// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.control.Label;
// import javafx.stage.Stage;

// public class App extends Application {
//     public static void main(String[] args) {
//         launch(args);
//     }

//     @Override
//     public void start(Stage primaryStage) {
//         Label label = new Label("Hello, JavaFX!");
//         Scene scene = new Scene(label, 400, 200);
//         primaryStage.setScene(scene);
//         primaryStage.setTitle("JavaFX Example");
//         primaryStage.show();
//     }
// }
