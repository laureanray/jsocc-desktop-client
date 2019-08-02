package com.fozf.jsocc.controllers;

import com.fozf.jsocc.utils.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LoginController {
    @FXML
    public Text subtitle;

    @FXML
    public Button loginButton;

    @FXML
    public Hyperlink registerLink;

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
                URL url = new File("src/com/fozf/resources/exercise.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Scene scene = new Scene(loader.load(), App.WINDOW_WIDTH, App.WINDOW_HEIGHT);
                Stage stage = new Stage();
                stage.setTitle("Dashboard");
                stage.setScene(scene);
                ExerciseController exerciseController = loader.getController();
                exerciseController.setStage(stage);
                stage.getIcons().add(new Image(getClass().getResourceAsStream("images/icon.png")));
                stage.show();


            } catch (IOException err) {
                System.out.println("Error");
                err.printStackTrace();
            }

        });

        registerLink.setOnAction(e -> {
            System.out.println("Register clicked");
            try {
                URL url = new File("src/com/fozf/resources/register.fxml").toURI().toURL();
                Scene scene = new Scene(FXMLLoader.load(url), App.SM_WIDTH, App.SM_HEIGHT);
                Stage stage = new Stage();
                stage.setTitle("Register");
                stage.setScene(scene);
                stage.getIcons().add(new Image(getClass().getResourceAsStream("images/icon.png")));
                stage.show();

//                ((Node)(e.getSource())).getScene().getWindow().hide();
            } catch (IOException err) {
                System.out.println("Error");
                err.printStackTrace();
            }
        });
    }

    public void close(){
        System.out.println("Close");
    }
}
