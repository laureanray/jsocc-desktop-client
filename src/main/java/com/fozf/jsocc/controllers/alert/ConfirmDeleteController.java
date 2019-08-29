package com.fozf.jsocc.controllers.alert;

import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.utils.CourseREST;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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
//            CourseREST.deleteCourse(this.course);
            deleteButton.setDisable(true);

            Task<Void> deleteButtonTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    CourseREST.deleteCourse(course);
                    return null;
                }
            };

            deleteButtonTask.setOnFailed(event -> {
                deleteButton.setDisable(false);
            });

            deleteButtonTask.setOnSucceeded(event -> {
                Stage stage = (Stage) deleteButton.getScene().getWindow();
                stage.close();
            });

            Thread deleteButtonThread = new Thread(deleteButtonTask);
            deleteButtonThread.setDaemon(true);
            deleteButtonThread.start();
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
