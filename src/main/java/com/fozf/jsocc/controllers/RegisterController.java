package com.fozf.jsocc.controllers;

import com.fozf.jsocc.models.Instructor;
import com.fozf.jsocc.models.Student;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.rest.InstructorREST;
import com.fozf.jsocc.utils.rest.StudentREST;
import com.fozf.jsocc.utils.ViewBootstrapper;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.ws.rs.core.Response;
import java.io.IOException;
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
    public Text errorText;

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

        registerBtn.setDisable(true);

        // Add event listeners
        firstName.setOnKeyReleased(e -> checkInputs(e.getCode()));
        lastName.setOnKeyReleased(e -> checkInputs(e.getCode()));
        username.setOnKeyReleased(e -> checkInputs(e.getCode()));
        email.setOnKeyReleased(e -> checkInputs(e.getCode()));
        password.setOnKeyReleased(e -> checkInputs(e.getCode()));
        confirmPassword.setOnKeyReleased(e -> checkInputs(e.getCode()));


        registerBtn.setOnAction(e -> {
            final Response[] res = new Response[1];

            Task<Void> task = null;

            if(isStudent.get()){
                // Validate the password
                if(confirmPassword.getText().equals(password.getText())){
                    Student student = new Student();
                    student.setEmail(email.getText());
                    student.setPassword(password.getText());
                    student.setFirstName(firstName.getText());
                    student.setLastName(lastName.getText());
                    student.setUsername(username.getText());
                    registerBtn.setText("Registering");
                    registerBtn.setDisable(true);


                    task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            res[0] = StudentREST.addStudent(student);
                            return null;
                        }
                    };

                }else{
                   errorText.setText("Passwords don't match.");
                   errorText.setVisible(true);
                }
            }else{
                // Handle instructor registration
                if(confirmPassword.getText().equals(password.getText())){
                    Instructor instructor = new Instructor();
                    instructor.setEmail(email.getText());
                    instructor.setPassword(password.getText());
                    instructor.setFirstName(firstName.getText());
                    instructor.setLastName(lastName.getText());
                    instructor.setUsername(username.getText());
                    registerBtn.setText("Registering");
                    registerBtn.setDisable(true);


                    task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            res[0] = InstructorREST.addInstructor(instructor);
                            return null;
                        }
                    };

                }else{
                    errorText.setText("Passwords don't match.");
                    errorText.setVisible(true);
                }
            }

            if(task != null){

                task.setOnSucceeded(re -> {
                    registerBtn.setDisable(false);
                    registerBtn.setText("Register");
                    if(res[0].getStatus() == 201){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Registration success!");
                        alert.setContentText("Registration success, you can now login. ");
                        alert.showAndWait();

                        Stage thisStage = (Stage) confirmPassword.getScene().getWindow();
                        thisStage.close();

                        try {
                            ViewBootstrapper vb = new ViewBootstrapper("Login", ViewBootstrapper.Size.SMALL,  ViewBootstrapper.Type.NORMAL);
                            LoginController lc = (LoginController) vb.getLoader().getController();
                            lc.username.setText(username.getText());
                            lc.password.setText(password.getText());
                            lc.loginButton.setDisable(false);
                            vb.getStage().show();

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }


                    }else{
                        errorText.setText("Username or email is already taken");
                        errorText.setVisible(true);
                    }
                });

            }

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();

        });


    }

    private void checkInputs(KeyCode code){
        if( firstName.getText().isEmpty() ||
                lastName.getText().isEmpty() ||
                username.getText().isEmpty() ||
                email.getText().isEmpty() ||
                password.getText().isEmpty() ||
                confirmPassword.getText().isEmpty()
        ){
            registerBtn.setDisable(true);
            if(code.equals(KeyCode.ENTER)){
                registerBtn.fire();
            }
        }else{
            registerBtn.setDisable(false);
        }
    }
}
