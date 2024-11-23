package controller.comman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Database.database;

public class signUpController {

    @FXML
    private TextField NameField;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> hostelComboBox;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private TextField phoneField1;

    @FXML
    private Button signinButton;

    @FXML
    private Button signupButton;

    @FXML
    private TextField userNameField;


    public void initialize() {
        hostelComboBox.getItems().addAll("student", "maintenance_staff", "administrator");
    }

    @FXML
    void handleSignIn(ActionEvent event) {
        System.out.println("Sign In");

    }

    @FXML
    void handleSignUp(ActionEvent event) {
        // System.out.println("Sign Up");
        // System.out.println("Email: " + emailField.getText());
        // System.out.println("Hostel: " + hostelComboBox.getValue());
        // System.out.println("Password: " + passwordField.getText());
        // System.out.println("Confirm Password: " + passwordField1.getText());
        // System.out.println("Username: " + userNameField.getText());

        // // TODO: Add Checks for empty fields and password match
        database.SignUp(userNameField.getText(), NameField.getText(), emailField.getText(), phoneField1.getText(), passwordField.getText(), hostelComboBox.getValue());
    }

    @FXML
    void handleSelectHostel(ActionEvent event) {
        System.out.println("Hostel Selected");
        System.out.println("Hostel: " + hostelComboBox.getValue());
    }

}
