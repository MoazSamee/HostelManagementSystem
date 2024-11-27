package controller.comman;

import javax.swing.JOptionPane;

import controller.Administrator.AdminController;
import controller.MaintenanceStaff.MaintenanceStaffController;
import controller.Student.StudentController;
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
import view.comman.signin_page;
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
            System.out.println("User Found");
            openUserPage(user);
        } else {
            System.out.println("User Not Found");
            JOptionPane.showMessageDialog(null, "User Not Found", "Error", JOptionPane.ERROR_MESSAGE);
            clearFields();
        }
    }

    @FXML
    void handleSignUp(ActionEvent event) {
        System.out.println("Sign Up Button Clicked");
        openSignUpPage();
    }

    @SuppressWarnings("unused")
    private void openUserPage(UserModel user) {
        try {
            // Get the current sign-in window stage
            Stage signInStage = (Stage) signinButton.getScene().getWindow();
            signInStage.hide(); // Hide the sign-in window
    
            Stage primaryStage = new Stage();
    
            // Open the appropriate user page
            if (user instanceof AdministratorModel) {
                System.out.println("User is Administrator");
                AdminPage adminPage = new AdminPage();
                adminPage.start(primaryStage);
                adminPage.setAdminController(new AdminController((AdministratorModel) user));
    
            } else if (user instanceof MaintenanceStaffModel) {
                System.out.println("User is Maintenance Staff");
                MaintenanceStaffView maintenanceStaffView = new MaintenanceStaffView();
                maintenanceStaffView.start(primaryStage);
                maintenanceStaffView.setMaintenanceStaffController(new MaintenanceStaffController((MaintenanceStaffModel) user));
            } else if (user instanceof StudentModel) {
                System.out.println("User is Student");
                StudentPage studentPage = new StudentPage();
                studentPage.start(primaryStage);
                studentPage.setStudentController(new StudentController((StudentModel) user));
            }
    
            // Add a listener to detect when the user page is closed
            primaryStage.setOnHiding(event -> {
                System.out.println("User Page Closed. Reopening Sign-In Page.");
                signInStage.show(); // Reopen the sign-in window
            });
    
            clearFields(); // Clear fields for a fresh start
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unused")
    private void openSignUpPage() {
        try {
            Stage signInStage = (Stage) signinButton.getScene().getWindow();
            signInStage.hide(); // Hide the sign-in window
    
            Stage primaryStage = new Stage();
    
            signup_page signup = new signup_page();
            signup.start(primaryStage);
    
            // Add a listener to detect when the sign-up page is closed
            primaryStage.setOnHiding(event -> {
                System.out.println("Sign-Up Page Closed. Reopening Sign-In Page.");
                signInStage.show(); // Reopen the sign-in window
            });
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void clearFields() {
        userNameField.setText("");
        passwordField.setText("");
    }

    public void showLoginPage() {
        try {
            System.out.println("Navigating to Login Page");
            Stage primaryStage = new Stage();
            signin_page page = new signin_page();
            page.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
