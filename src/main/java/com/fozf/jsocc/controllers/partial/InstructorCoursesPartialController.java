package com.fozf.jsocc.controllers.partial;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;


public class InstructorCoursesPartialController {
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox vbox;
    @FXML
    public Button createNewCourseButton;
    @FXML
    public VBox courseForm;

    @FXML
    public void initialize(){
        courseForm.setVisible(false);
        ObservableList<Node> nodes = courseForm.getChildren();
        courseForm.getChildren().removeAll(nodes);
        courseForm.setMaxWidth(0);
        // Add event listener
        createNewCourseButton.setOnAction(e -> {
            courseForm.getChildren().addAll(nodes);
            courseForm.setMaxWidth(500);
            courseForm.setVisible(true);
        });
    }



}
