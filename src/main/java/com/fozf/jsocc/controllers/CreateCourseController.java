package com.fozf.jsocc.controllers;

import com.fozf.jsocc.controllers.partial.InstructorCoursesPartialController;
import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.models.CourseTemplate;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.rest.CourseREST;
import com.fozf.jsocc.utils.rest.CourseTemplateREST;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CreateCourseController {
    @FXML
    private Button createCourseButton;
    @FXML
    private TextField  courseCode, enrollmentKey, enrollmentKeyConfirm;
    @FXML
    private TextArea courseDescription;
    @FXML
    private DatePicker startDate, endDate;
    @FXML
    private ChoiceBox<String> courseTemplate, programmingLanguage;

    private List<CourseTemplate> courseTemplates = new ArrayList<CourseTemplate>();

    private InstructorCoursesPartialController controller;

    static Response response;

    @FXML
    public void initialize(){
        System.out.println("Create course controller initialized");

        // Set the programming language options
        programmingLanguage.getItems().clear();
        programmingLanguage.getItems().add("Java");
        programmingLanguage.getItems().add("Python");

        Task<Void> getCourseTemplateTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                courseTemplates = new ArrayList<CourseTemplate>(CourseTemplateREST.findByProgrammingLanguage("Java"));
                return null;
            }
        };

        getCourseTemplateTask.setOnSucceeded(ev -> {
            courseTemplate.setDisable(false);
            for(CourseTemplate template : courseTemplates){
                courseTemplate.getItems().add(template.getCourseTitle());
            }
        });

        Thread getCourseTemplateThread = new Thread(getCourseTemplateTask);
        getCourseTemplateThread.setDaemon(true);
        getCourseTemplateThread.start();

        // Add event listener when select is changed
        programmingLanguage.setOnAction(ev -> {
            System.out.println(programmingLanguage.getSelectionModel().getSelectedItem());
        });


        // Disable first while fetching
        courseTemplate.setDisable(true);

        createCourseButton.setDisable(true);

        createCourseButton.setOnAction(event -> {
            Course course = new Course();
            course.setCourseDescription(courseDescription.getText());
            course.setCourseCode(courseCode.getText());
            course.setEnrollmentKey(enrollmentKey.getText());
            course.setEnrollmentStartDate(Date.valueOf(startDate.getValue()));
            course.setEnrollmentEndDate(Date.valueOf(endDate.getValue()));
            course.setInstructor(App.instructor);

            Task<Void> createCourseTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    response = CourseREST.createCourse(course);
                    return null;
                }
            };

            Thread createCourseThread = new Thread(createCourseTask);
            createCourseThread.setDaemon(true);


            createCourseTask.setOnSucceeded(ev -> {
                Stage stage = (Stage) createCourseButton.getScene().getWindow();

                // if status is 201 update the table
                if(response.getStatus() == 201){
                    if(this.controller != null){
                        this.controller.updateTableAsync();
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Courses created!");
                    alert.setContentText("Course has been created");
                    alert.showAndWait();
                    stage.close();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(String.valueOf(response.getStatus()));
                    alert.setContentText(String.valueOf(response.getStatusInfo()));
                    alert.showAndWait();
                    stage.close();
                }

            });

            createCourseTask.setOnFailed(ev -> {
                System.out.println(response);
            });

            createCourseThread.start();
        });

//        courseTitle.setOnKeyReleased(this::checkInput);
        courseCode.setOnKeyReleased(this::checkInput);
        courseDescription.setOnKeyReleased(this::checkInput);
        enrollmentKey.setOnKeyReleased(this::checkInput);
        enrollmentKeyConfirm.setOnKeyReleased(this::checkInput);
        startDate.setOnAction(this::checkInput);
        endDate.setOnAction(this::checkInput);
    }

    private void checkInput(ActionEvent actionEvent) {
        validate();
    }

    private void validate() {
        if(
                courseDescription.getText().length() > 0 &&
                courseCode.getText().length() > 0 &&
                enrollmentKey.getText().length() > 0 &&
                enrollmentKeyConfirm.getText().length() > 0 &&
                startDate.getValue() != null &&
                endDate.getValue() != null
        ) {
            if(enrollmentKey.getText().equals(enrollmentKeyConfirm.getText())){
                createCourseButton.setDisable(false);
            }
        }
    }

    public void setController(InstructorCoursesPartialController controller){
        System.out.println("Controller set");
        this.controller = controller;
    }

    private void checkInput(KeyEvent event){
        validate();
    }
}
