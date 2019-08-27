package com.fozf.jsocc.controllers.alert;

import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.utils.CourseREST;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ConfirmDeleteController {
    @FXML
    private Button deleteButton;
    @FXML
    private TextField courseTitleTextField;

    private Course course;

    @FXML
    public void initialize(){
        System.out.println("Confirm Delete initialized");
        deleteButton.setDisable(true);

        courseTitleTextField.setOnKeyReleased(this::onInputChanged);

        deleteButton.setOnAction(ev -> {
            CourseREST.deleteCourse(this.course);
        });
    }



    public void setCourse(Course course) {
        this.course = course;
        courseTitleTextField.setAccessibleText(course.getCourseTitle());
    }

    private void onInputChanged(KeyEvent event){
        if(courseTitleTextField.getText().equals(course.getCourseTitle())){
            deleteButton.setDisable(false);
        }
    }

}
