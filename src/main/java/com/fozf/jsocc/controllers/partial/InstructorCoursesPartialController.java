package com.fozf.jsocc.controllers.partial;

import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.CourseRest;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javax.ws.rs.core.Response;


public class InstructorCoursesPartialController {
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox vbox;
    @FXML
    public Button createNewCourseButton, createCourseButton, cancelButton;
    @FXML
    public VBox courseForm;
    @FXML
    public TableView courseTable;
    @FXML
    public TextField courseTitle, enrollmentKey, courseCode;
    @FXML
    public TextArea courseDescription;



    @FXML
    public void initialize(){
        courseForm.setVisible(false);
        // Add event listener
        createNewCourseButton.setOnAction(e -> {
            courseForm.setVisible(true);
        });

        createCourseButton.setDisable(true);

        courseTitle.setOnKeyReleased(e -> {
            if(isValidInput()){
                createCourseButton.setDisable(false);
            }
        });

        courseDescription.setOnKeyReleased(e -> {
            if(isValidInput()){
                createCourseButton.setDisable(false);
            }
        });

        enrollmentKey.setOnKeyReleased(e -> {
            if(isValidInput()){
                createCourseButton.setDisable(false);
            }
        });

        courseCode.setOnKeyReleased(e -> {
            if(isValidInput()){
                createCourseButton.setDisable(false);
            }
        });

        createCourseButton.setOnAction(e -> {
            Course course = new Course();
            course.setCourseTitle(courseTitle.getText());
            course.setCourseCode(courseCode.getText());
            course.setCourseDescription(courseDescription.getText());
            course.setEnrollmentKey(enrollmentKey.getText());
            course.setInstructorId(App.instructor.getId());

            final Response[] res = new Response[1];

            Task<Void> createCourseTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    res[0] = CourseRest.createCourse(course);
                    return null;
                }
            };

            createCourseTask.setOnSucceeded(ez -> {
                if(res[0].getStatus() == 201){
                    // this means created
                    System.out.println("Success");
                }else{
                    System.out.println("Failed");
                }
            });

            Thread createCourseThread = new Thread(createCourseTask);

            createCourseThread.setDaemon(true);
            createCourseThread.start();


        });



    }

    private boolean isValidInput() {
        // This function checks the inputs for the create course form
        return !courseTitle.getText().isEmpty() &&
                !courseDescription.getText().isEmpty() &&
                !enrollmentKey.getText().isEmpty() &&
                !courseCode.getText().isEmpty();
    }




}
