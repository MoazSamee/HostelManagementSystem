package controller.comman;

import javax.swing.JOptionPane;

import controller.Administrator.AdminController;
import controller.MaintenanceStaff.MaintenanceStaffController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Database.database;
import model.User.AdministratorModel;
import model.User.MaintenanceStaffModel;
import model.User.StudentModel;
import model.User.UserModel;
import view.Administrator.AdminPage;
import view.MaintenanceStaff.MaintenanceStaffView;
import view.Student.StudentPage;
import view.comman.signup_page;

public class signInController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label signUpButton;

    @FXML
    private Button signinButton;

    @FXML
    private Button signupButton;

    @FXML
    private TextField userNameField;

    @FXML
    void handleSignIn(ActionEvent event) {
        System.out.println("Sign In Button Clicked");
        UserModel user = database.SignIn(userNameField.getText(), passwordField.getText());
        
        if (user != null) {
            // open user page
            System.out.println("User Found");
            if (user.getClass().getName().equals("model.User.AdministratorModel")) {
                System.out.println("User is Administrator");
                AdminPage adminPage = new AdminPage();
                Stage primaryStage = new Stage();
                try {
                    adminPage.start(primaryStage);
                    userNameField.setText("");
                    passwordField.setText("");
                    adminPage.setAdminController(new AdminController((AdministratorModel) user));
                    this.signinButton.getScene().getWindow().hide();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else if (user.getClass().getName().equals("model.User.MaintenanceStaffModel"))
            {
                System.out.println("User is Maintenance Staff");
                MaintenanceStaffView maintenanceStaffView = new MaintenanceStaffView();
                Stage primaryStage = new Stage();
                try {
                    maintenanceStaffView.start(primaryStage);
                    userNameField.setText("");
                    passwordField.setText("");
                    maintenanceStaffView.setMaintenanceStaffController(new MaintenanceStaffController((MaintenanceStaffModel) user));
                    this.signinButton.getScene().getWindow().hide();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else if (user.getClass().getName().equals("model.User.StudentModel")) 
            {
                System.out.println("User is Student");
                StudentPage studentPage = new StudentPage();
                Stage primaryStage = new Stage();
                try {
                    studentPage.start(primaryStage);
                    userNameField.setText("");
                    passwordField.setText("");
                    // studentPage.setStudent((StudentModel) user);
                    studentPage.getStudentController().setStudent((StudentModel) user);
                    this.signinButton.getScene().getWindow().hide();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } else {
            System.out.println("User Not Found");
            JOptionPane.showMessageDialog(null, "User Not Found", "Error", JOptionPane.ERROR_MESSAGE);
            userNameField.setText("");
            passwordField.setText("");
        }

    }

    @FXML
    void handleSignUp(ActionEvent event) {
        System.out.println("Sign Up Button Clicked");
        // this.signUpButton.getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        signup_page signup = new signup_page();
        try {
            signup.start(primaryStage);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
