package com.fozf.jsocc.controllers;

import com.fozf.jsocc.models.Course;
import com.sun.xml.internal.bind.XmlAccessorFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.sql.Date;

public class CreateCourseController {
    @FXML
    private Button createCourseButton;
    @FXML
    private TextField courseTitle, courseCode, enrollmentKey, enrollmentKeyConfirm;
    @FXML
    private TextArea courseDescription;
    @FXML
    private DatePicker startDate, endDate;

    @FXML
    public void initialize(){
        System.out.println("Create course controller initialized");

        // Add click listener
        createCourseButton.setOnAction(event -> {
            Course course = new Course();
            course.setCourseTitle(courseTitle.getText());
            course.setCourseDescription(courseDescription.getText());
            course.setCourseCode(courseCode.getText());
            course.setEnrollmentKey(enrollmentKey.getText());
            course.setEnrollmentStartDate(Date.valueOf(startDate.getValue()));
            course.setEnrollmentEndDate(Date.valueOf(endDate.getValue()));
        });

        courseTitle.setOnKeyReleased(this::checkInput);
        courseCode.setOnKeyReleased(this::checkInput);
        courseDescription.setOnKeyReleased(this::checkInput);
        enrollmentKey.setOnKeyReleased(this::checkInput);
        enrollmentKeyConfirm.setOnKeyReleased(this::checkInput);
        startDate.setOnKeyReleased(this::checkInput);
        endDate.setOnKeyReleased(this::checkInput);





    }

    private void checkInput(KeyEvent event){

    }
}
