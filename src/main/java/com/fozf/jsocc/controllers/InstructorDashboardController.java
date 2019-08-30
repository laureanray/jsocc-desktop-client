package com.fozf.jsocc.controllers;

import com.fozf.jsocc.controllers.partial.InstructorCoursesPartialController;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.ViewBootstrapper;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
            try {
                ViewBootstrapper view = changeUI("instructorCoursesPartial");
                InstructorCoursesPartialController controller = view.getLoader().getController();
                controller.setDashboardController(this);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        dashboardLink.setOnAction(e -> {
            try {
                ViewBootstrapper view = changeUI("instructorDashboardPartial");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
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

    public ViewBootstrapper changeUI(String filename) throws IOException {

            ViewBootstrapper view = new ViewBootstrapper(filename, ViewBootstrapper.Size.INSIDE,  ViewBootstrapper.Type.PARTIAL);
            Node root = view.getLoader().getRoot();
            rootPane.setCenter(root);

            root.opacityProperty().set(0);
            Timeline timeline = new Timeline();

            List<KeyValue> keyValues = new ArrayList<>();
            KeyValue kv2= new KeyValue(root.opacityProperty(), 1, Interpolator.EASE_IN);

            KeyFrame kf2 = new KeyFrame(Duration.seconds(0.2), kv2);
            timeline.getKeyFrames().add(kf2);

            timeline.play();


        return view;
    }

    private void showLogoutDialog(Event e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Logging out.");
        alert.setContentText("Are you sure you want to do this? ");
        Optional<ButtonType> result  = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            try {
                new ViewBootstrapper("Login", ViewBootstrapper.Size.SMALL,  ViewBootstrapper.Type.NORMAL).getStage().show();
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
