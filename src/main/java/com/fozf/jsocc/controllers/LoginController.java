package com.fozf.jsocc.controllers;

import com.fozf.jsocc.models.LoginForm;
import com.fozf.jsocc.models.Student;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.Server;
import com.fozf.jsocc.utils.StudentRest;
import com.fozf.jsocc.utils.ViewBootstrap;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.ws.rs.core.Response;
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
                ViewBootstrap vb = new ViewBootstrap("Register", ViewBootstrap.Size.SMALL);
                vb.getStage().show();
                Stage thisStage = (Stage) registerLink.getScene().getWindow();
                thisStage.close();
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
            loginButton.setText("Signing in...");
            LoginForm lf = new LoginForm();
            lf.setPassword(password.getText());
            lf.setUsername(username.getText());
            final Response[] res = new Response[1];
            Task<Void> loginTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    res[0] = StudentRest.login(lf);
                    return null;
                }
            };

            final Student[] student = new Student[1];

            Task<Void> getStudentInfoTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    student[0] = StudentRest.getStudentByUsername(username.getText());
                    return null;
                }
            };


            getStudentInfoTask.setOnSucceeded(s -> {
                App.isStudent = true;
                App.student = student[0];
                try {
                    ViewBootstrap vb = new ViewBootstrap("Student Dashboard", ViewBootstrap.Size.LARGE);
                    vb.getStage().show();
                    StudentDashboardController studentDashboardController = (StudentDashboardController) vb.getLoader().getController();
                    studentDashboardController.setStage(vb.getStage());
                    Stage thisStage = (Stage) loginButton.getScene().getWindow();
                    thisStage.close();
                } catch (IOException err) {
                    System.out.println("Error");
                    err.printStackTrace();
                }
            });

            Thread loginThread = new Thread(loginTask);
            Thread getStudentThread = new Thread(getStudentInfoTask);
            getStudentThread.setDaemon(true);
            loginThread.setDaemon(true);
            loginThread.start();

            loginTask.setOnSucceeded(er -> {
                loginButton.setDisable(false);
                loginButton.setText("Sign In");

                if(res[0].getStatus() == 200){
                    // Get student information
                        getStudentThread.start();
                }else{
                    errorText.setText("Invalid login credentials.");
                    errorText.setVisible(true);
                }
            });







        });

        username.setOnKeyReleased(e -> checkInputFields(e.getCode()));
        password.setOnKeyReleased(e -> checkInputFields(e.getCode()));

        timer.schedule(checkServerConnection, 500L);
    }

    public void close(){
        System.out.println("Close");
    }

    private void checkInputFields(KeyCode code){
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            loginButton.setDisable(true);
        }else{
            loginButton.setDisable(false);
            if(code.equals(KeyCode.ENTER)){
                loginButton.fire();
            }
        }
    }
}
