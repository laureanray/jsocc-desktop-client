package com.fozf.jsocc.controllers;

import com.fozf.jsocc.models.Student;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.StudentRest;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterController {
    @FXML
    public Text subtitle;
    @FXML
    public TextField firstName;
    @FXML
    public TextField lastName;
    @FXML
    public TextField email;
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
        AtomicBoolean isStudent = new AtomicBoolean(true);
        checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                System.out.println("Checked");
                registerBtn.setText("Register as Instructor");
                isStudent.set(false);
            }else {
                registerBtn.setText("Register as Student");
                isStudent.set(true);
            }
        });

        registerBtn.setOnAction(e -> {
            if(isStudent.get()){
                // Validate the password
                if(confirmPassword.getText().equals(password.getText())){
                    Student student = new Student();
                    student.setEmail(email.getText());
                    student.setPassword(password.getText());
                    student.setFirstName(firstName.getText());
                    student.setLastName(lastName.getText());
                    student.setUsername(username.getText());
                    Response res = StudentRest.addStudent(student);
                    System.out.println(res.getStatus());
                    if(res.getStatus() == 201){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Registration success!");
                        alert.setContentText("Registration success, you can now login. ");
                        alert.showAndWait();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Passwords don't match");
                    alert.setContentText("Make sure that both passwords are the same.");
                    alert.showAndWait();
                }
            }
        });


    }
}
