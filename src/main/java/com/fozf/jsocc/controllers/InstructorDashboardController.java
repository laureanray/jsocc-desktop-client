package com.fozf.jsocc.controllers;

import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.ViewBootstrap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class InstructorDashboardController {
    private Stage stage;

    String fullName;

    @FXML
    public MenuButton accountMenu;

    @FXML
    public BorderPane rootPane;

    @FXML
    public Text accountName;

    @FXML
    public Hyperlink coursesLink;

    public InstructorDashboardController(){
        if(App.instructor == null || App.isStudent){
            throw new RuntimeException("Must be instructor show this dashboard.");
        }
        this.fullName = App.instructor.getFirstName() + " " + App.instructor.getLastName();
    }

    @FXML
    public void initialize(){
        System.out.println("Login instructor: ");
        System.out.println(App.instructor.getFirstName());
        accountMenu.setText(fullName);
        accountName.setText(fullName);

        coursesLink.setOnAction(e -> {
            try {
                rootPane.setCenter(FXMLLoader.load(getClass().getResource("/fxml/partial/instructorCoursesPartial.fxml")));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

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
