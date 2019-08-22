package com.fozf.jsocc.controllers;

import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.ViewBootstrap;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
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
    public Hyperlink coursesLink, dashboardLink;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public MenuItem logoutMenuItem;


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
            changeUI("instructorCoursesPartial");
        });

        dashboardLink.setOnAction(e -> {
            changeUI("instructorDashboardPartial");
        });

        logoutMenuItem.setOnAction(this::showLogoutDialog);

        dashboardLink.fire();



    }

    void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(e -> {
            System.out.println("Closing");
            showLogoutDialog(e);
        });
    }

    void changeUI(String filename){
        try {
            Node n = FXMLLoader.load(getClass().getResource("/fxml/partial/"+filename+".fxml"));
            rootPane.setCenter(n);

//            n.translateYProperty().set(100.0);
            n.opacityProperty().set(0);
            Timeline timeline = new Timeline();

            List<KeyValue> keyValues = new ArrayList<>();
//            KeyValue kv1 = new KeyValue(n.translateYProperty(), 0, Interpolator.EASE_IN);
            KeyValue kv2= new KeyValue(n.opacityProperty(), 1, Interpolator.EASE_IN);

//            KeyFrame kf1 = new KeyFrame(Duration.seconds(0.3), kv1);
            KeyFrame kf2 = new KeyFrame(Duration.seconds(0.2), kv2);
//            timeline.getKeyFrames().add(kf1);
            timeline.getKeyFrames().add(kf2);

;           timeline.play();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }






    private void showLogoutDialog(Event e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Logging out.");
        alert.setContentText("Are you sure you want to do this? ");
        Optional<ButtonType> result  = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            try {
                new ViewBootstrap("Login", ViewBootstrap.Size.SMALL).getStage().show();
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
