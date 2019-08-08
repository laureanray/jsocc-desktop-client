package com.fozf.jsocc.controllers;

import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.ViewBootstrap;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class InstructorDashboardController {
    private Stage stage;


    @FXML
    public MenuButton accountMenu;

    public InstructorDashboardController(){
        if(App.instructor == null || App.isStudent){
            throw new RuntimeException("Must be instructor show this dashboard.");
        }
    }

    @FXML
    public void initialize(){
        System.out.println("Login instructor: ");
        System.out.println(App.instructor.getFirstName());
        accountMenu.setText(App.instructor.getFirstName() + " " + App.instructor.getLastName());
    }

    void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(e -> {
            System.out.println("Closing");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText("Logging out.");
            alert.setContentText("Are you sure you want to do this? ");
            Optional<ButtonType> result  = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                try {
                    new ViewBootstrap("Login", ViewBootstrap.Size.SMALL).getStage().show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }else{
                e.consume();
            }
        });
    }


}
