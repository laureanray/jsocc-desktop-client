package com.fozf.jsocc.controllers;

import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.Server;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
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

    // This class timer
    private Timer timer = new Timer();

    public LoginController(){
        System.out.println("Constructor");
    }

    @FXML
    public void initialize(){
        System.out.println("Initialize");
        subtitle.setText(App.name);

        loginButton.setOnAction(e -> {
            System.out.println("Login Button");
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


            } catch (IOException err) {
                System.out.println("Error");
                err.printStackTrace();
            }

        });

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



        timer.schedule(checkServerConnection, 500L);
    }

    public void close(){
        System.out.println("Close");
    }
}
