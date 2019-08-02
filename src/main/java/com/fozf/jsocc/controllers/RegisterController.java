package com.fozf.jsocc.controllers;

import com.fozf.jsocc.models.Student;
import com.fozf.jsocc.utils.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegisterController {
    @FXML
    public Text subtitle;
    @FXML
    public TextField firstName;
    @FXML
    public TextField lastName;
    @FXML
    public TextField emailAddress;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public PasswordField confirmPassword;
    @FXML
    public CheckBox checkbox;
    @FXML
    public Button registerBtn;

    @FXML
    public void initialize(){
        subtitle.setText(App.name);
        checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                System.out.println("Checked");
                registerBtn.setText("Register as Instructor");
            }else {
                registerBtn.setText("Register as Student");
            }
        });

        if(registerBtn.getText().equals("Register as Student")){
            // Register as student.
            Student student = new Student(
                    firstName.getText(),
                    lastName.getText(),
                    emailAddress.getText(),
                    password.getText(),
                    username.getText());
        }


    }
}
