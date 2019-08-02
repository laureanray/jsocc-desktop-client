package com.fozf.jsocc.controllers;

import com.fozf.jsocc.models.LoginForm;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.Server;
import com.fozf.jsocc.utils.StudentRest;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/register.fxml")), App.SM_WIDTH, App.SM_HEIGHT);
                Stage stage = new Stage();
                stage.setTitle("Register");
                stage.setScene(scene);
                stage.getIcons().add(App.icon);
                stage.show();

//                ((Node)(e.getSource())).getScene().getWindow().hide();
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
                }else{
                    serverStatus.setText("No connection.");
                    checkLink.setVisible(true);
                }
            }
        };

        checkLink.setOnAction(e -> {
            checkLink.setVisible(false);
            serverStatus.setText("Checking...");
            if(Server.checkServer()){
                serverStatus.setText("Ok");
            }else{
                serverStatus.setText("No connection.");
                checkLink.setVisible(true);
            }
        });


        loginButton.setOnAction(e -> {
            loginButton.setDisable(true);
            loginButton.setText("Signing in...");
            LoginForm lf = new LoginForm();
            lf.setPassword(password.getText());
            lf.setUsername(username.getText());
            final Response[] res = new Response[1];
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    res[0] = StudentRest.login(lf);
                    return null;
                }
            };

            task.setOnSucceeded(er -> {
                loginButton.setDisable(false);
                loginButton.setText("Sign In");

                if(res[0].getStatus() == 200){
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/exercise.fxml"));
                        Scene scene = new Scene(loader.load(), App.WINDOW_WIDTH, App.WINDOW_HEIGHT);
                        Stage stage = new Stage();
                        stage.setTitle("Dashboard");
                        stage.setScene(scene);
                        ExerciseController exerciseController = loader.getController();
                        exerciseController.setStage(stage);
                        stage.getIcons().add(App.icon);
                        stage.show();

                        Stage thisStage = (Stage) loginButton.getScene().getWindow();
                        // do what you have to do
                        thisStage.close();

                    } catch (IOException err) {
                        System.out.println("Error");
                        err.printStackTrace();
                    }
                }else{
                    errorText.setText("Invalid login credentials.");
                    errorText.setVisible(true);
                }
            });

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();




        });

        username.setOnKeyReleased(e -> checkInputFields(e.getCode()));
        password.setOnKeyReleased(e -> checkInputFields(e.getCode()));

        timer.schedule(checkServerConnection, 500L);
    }

    public void close(){
        System.out.println("Close");
    }

    private void checkInputFields(KeyCode code){
        System.out.println(code);
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
