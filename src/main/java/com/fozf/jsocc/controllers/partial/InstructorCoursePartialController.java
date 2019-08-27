package com.fozf.jsocc.controllers.partial;

import com.fozf.jsocc.models.Course;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class InstructorCoursePartialController {
    @FXML
    private Text courseTitle, courseDescription;

    @FXML
    private TextFlow textFlow;

    private Course course;

    @FXML
    public void initialize(){

    }

    public void setCourse(Course course) {
        this.course = course;

        initializeUI();
    }

    private void initializeUI(){
        courseTitle.setText(course.getCourseTitle());
        courseDescription.setText(course.getCourseDescription());
    }
}
