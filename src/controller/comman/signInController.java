package controller.comman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        System.out.println("Username: " + userNameField.getText());
        System.out.println("Password: " + passwordField.getText());

    }

    @FXML
    void handleSignUp(ActionEvent event) {
        System.out.println("Sign Up Button Clicked");

    }

}
