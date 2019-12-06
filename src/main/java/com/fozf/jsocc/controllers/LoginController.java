package com.fozf.jsocc.controllers;

import com.fozf.jsocc.models.Instructor;
import com.fozf.jsocc.models.LoginForm;
import com.fozf.jsocc.models.Student;
import com.fozf.jsocc.models.User;
import com.fozf.jsocc.utils.*;
import com.fozf.jsocc.utils.rest.AuthREST;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LoginController {
    @FXML
    public Text subtitle;
    @FXML
    public Button loginButton;
    @FXML
    public Hyperlink registerLink;
    @FXML
    public Text serverStatus;
    @FXML
    public Hyperlink checkLink;
    @FXML
    public TextField username;
    @FXML
    public TextField password;
    @FXML
    public Text errorText;
    @FXML
    public ImageView statusImage;
    @FXML
    public BorderPane borderPane;

    private static User user;


    // This class timer
    private Timer timer = new Timer();

    public LoginController(){
        System.out.println("Constructor");
    }

    @FXML
    public void initialize(){


        System.out.println("Initialize");
        subtitle.setText(App.name);

        registerLink.setOnAction(e -> {
            System.out.println("Register clicked");
            try {
                ViewBootstrapper vb = new ViewBootstrapper("Register", ViewBootstrapper.Size.SMALL,   ViewBootstrapper.Type.NORMAL);
                Stage registerStage = vb.getStage();

                Stage thisStage = (Stage) registerLink.getScene().getWindow();
                thisStage.close();

                // Add close request callback
                registerStage.setOnCloseRequest(ecv -> {
                    thisStage.show();
                });

                registerStage.show();


            } catch (IOException err) {
                System.out.println("Error");
                err.printStackTrace();
            }
        });

        TimerTask checkServerConnection = new TimerTask(){
            public void run(){
                if(Server.checkServer()){
                    serverStatus.setText("Ok");
                    checkLink.setVisible(false);
                    statusImage.setImage(App.statusGreen);
                }else{
                    serverStatus.setText("No connection.");
                    statusImage.setImage(App.statusRed);
                    checkLink.setVisible(true);
                }
            }
        };



        checkLink.setOnAction(e -> {
            checkLink.setVisible(false);
            final boolean[] isServerOk = {false};
            serverStatus.setText("Checking...");

            Task<Void> checkServerTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    isServerOk[0] = Server.checkServer();
                    return null;
                }
            };

            checkServerTask.setOnSucceeded(s -> {
                System.out.println("On Succceed");
                if(isServerOk[0]){
                    serverStatus.setText("Ok");
                    statusImage.setImage(App.statusGreen);
                }else{
                    serverStatus.setText("No connection.");
                    statusImage.setImage(App.statusRed);
                    checkLink.setVisible(true);
                }
            });

            checkServerTask.setOnFailed(s -> {
                System.out.println("Failed");
            });
            checkServerTask.setOnCancelled(s -> {
                System.out.println("cancelled");
            });
            checkServerTask.setOnRunning(s -> {
                System.out.println("Running");
            });

            Thread checkServerThread = new Thread(checkServerTask);
            checkServerThread.setDaemon(true);
            checkServerThread.start();

        });


        loginButton.setOnAction(e -> {
            loginButton.setDisable(true);
            username.setDisable(true);
            password.setDisable(true);
            loginButton.setText("Signing in...");
            LoginForm lf = new LoginForm();
            lf.setPassword(password.getText());
            lf.setUsername(username.getText());


            Task<Void> studentLoginTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    user = AuthREST.login(lf);
                    return null;
                }
            };

            studentLoginTask.setOnSucceeded(s -> {
                ViewBootstrapper view = null;
                if(user.getClass().getSimpleName().equals("Instructor")){
                    try {
                        App.isStudent = false;
                        App.instructor = (Instructor) user;
                        App.student = null;
                        view = new ViewBootstrapper("Instructor Dashboard", ViewBootstrapper.Size.LARGE,  ViewBootstrapper.Type.NORMAL);
                        view.getStage().show();
                        InstructorDashboardController instructorDashboardController = (InstructorDashboardController) view.getLoader().getController();
                        instructorDashboardController.setStage(view.getStage());
                        Stage thisStage = (Stage) loginButton.getScene().getWindow();
                        thisStage.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }else{
                    try {
                        App.isStudent = true;
                        App.instructor = null;
                        App.student = (Student) user;
                        view = new ViewBootstrapper("Student Dashboard", ViewBootstrapper.Size.LARGE,  ViewBootstrapper.Type.NORMAL);
                        StudentDashboardController studentDashboardController = (StudentDashboardController) view.getLoader().getController();
                        view.getStage().show();

                        studentDashboardController.setStage(view.getStage());
                        Stage thisStage = (Stage) loginButton.getScene().getWindow();
                        thisStage.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            });

            studentLoginTask.setOnFailed(event -> {
                loginButton.setDisable(false);
                loginButton.setText("Login");
                errorText.setText("Invalid login credentials.");
                errorText.setVisible(true);
                username.setDisable(false);
                password.setDisable(false);
            });

            Thread loginThread = new Thread(studentLoginTask);
            // Make this background task
            loginThread.setDaemon(true);
            // Start the thread
            loginThread.start();
         });

        username.setOnKeyReleased(e -> checkInputFields(e.getCode()));
        password.setOnKeyReleased(e -> checkInputFields(e.getCode()));

        timer.schedule(checkServerConnection, 500L);
    }

    public void close(){
        // Code cleanup here?
        System.exit(0);
    }

    private void checkInputFields(KeyCode code){
        if(username.getText().isEmpty() || password.getText().isEmpty() || serverStatus.getText().equals("No connection.")){
            loginButton.setDisable(true);
        }else{
            loginButton.setDisable(false);
            if(code.equals(KeyCode.ENTER)){
                loginButton.fire();
            }
        }
    }
}
