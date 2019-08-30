package com.fozf.jsocc.controllers;

import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.ViewBootstrapper;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class StudentDashboardController {
    private Stage stage;


    @FXML
    public MenuButton accountMenu;

    @FXML
    public MenuItem logoutMenuItem;

    public StudentDashboardController(){
        if(App.student == null || !App.isStudent){
            throw new RuntimeException("Must be student to show this dashboard.");
        }
    }

    @FXML
    public void initialize(){
        System.out.println("Login student: ");
        System.out.println(App.student.getFirstName());
        accountMenu.setText(App.student.getFirstName() + " " + App.student.getLastName());
        logoutMenuItem.setOnAction(this::showLogoutDialog);

    }

    void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(e -> {
            System.out.println("Closing");
            showLogoutDialog(e);
        });
    }

    private void showLogoutDialog(Event e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Logging out.");
        alert.setContentText("Are you sure you want to do this? ");
        Optional<ButtonType> result  = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            try {
                new ViewBootstrapper("Login", ViewBootstrapper.Size.SMALL, ViewBootstrapper.Type.NORMAL).getStage().show();
                App.instructor = null;
                this.stage.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }else{
            e.consume();
        }


    }


}
