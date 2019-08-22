package com.fozf.jsocc.controllers.partial;

import com.fasterxml.jackson.databind.deser.impl.PropertyValue;
import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.CourseRest;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


public class InstructorCoursesPartialController {
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox vbox;
    @FXML
    public Button createNewCourseButton, createCourseButton, cancelButton;
//    @FXML
//    public VBox courseForm;
    @FXML
    public TableView courseTable;
//    @FXML
//    public TextField courseTitle, enrollmentKey, courseCode;
//    @FXML
//    blic TextArea courseDescription;

    private List<Course> courseList = new ArrayList<>();


    @FXML
    public void initialize(){
        // Udpate table
        System.out.println("Initialize Partial");
        initializeTable();
        populateTable();

//        courseForm.setVisible(false);
//        // Add event listener
//        createNewCourseButton.setOnAction(e -> {
//            courseForm.setVisible(true);
//        });
//
//        createCourseButton.setDisable(true);
//
//        courseTitle.setOnKeyReleased(e -> {
//            if(isValidInput()){
//                createCourseButton.setDisable(false);
//            }
//        });
//
//        courseDescription.setOnKeyReleased(e -> {
//            if(isValidInput()){
//                createCourseButton.setDisable(false);
//            }
//        });
//
//        enrollmentKey.setOnKeyReleased(e -> {
//            if(isValidInput()){
//                createCourseButton.setDisable(false);
//            }
//        });
//
//        courseCode.setOnKeyReleased(e -> {
//            if(isValidInput()){
//                createCourseButton.setDisable(false);
//            }
//        });

//        createCourseButton.setOnAction(e -> {
//            createCourseButton.setDisable(true);
//            Course course = new Course();
//            course.setCourseTitle(courseTitle.getText());
//            course.setCourseCode(courseCode.getText());
//            course.setCourseDescription(courseDescription.getText());
//            course.setEnrollmentKey(enrollmentKey.getText());
//            course.setInstructorId(App.instructor.getId());
//
//            final Response[] res = new Response[1];
//
//            Task<Void> createCourseTask = new Task<Void>() {
//                @Override
//                protected Void call() throws Exception {
//                    res[0] = CourseRest.createCourse(course);
//                    return null;
//                }
//            };
//
//            createCourseTask.setOnSucceeded(ez -> {
//                createCourseButton.setDisable(false);
//                if(res[0].getStatus() == 201){
//                    // this means created
//                    populateTable();
//                    clearInput();
//
//                    System.out.println("Success");
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Success");
//                    alert.setHeaderText("Course created");
//                    alert.setContentText("Course has been created please check the courses table to confirm the details");
//                    alert.showAndWait();
//
//
//                }else{
//                    System.out.println("Failed");
//                }
//            });
//
//            Thread createCourseThread = new Thread(createCourseTask);
//
//            createCourseThread.setDaemon(true);
//            createCourseThread.start();
//        });

    }

//    private boolean isValidInput() {
//        // This function checks the inputs for the create course form
//        return !courseTitle.getText().isEmpty() &&
//                !courseDescription.getText().isEmpty() &&
//                !enrollmentKey.getText().isEmpty() &&
//                !courseCode.getText().isEmpty();
//    }
//
//    private void clearInput() {
//        courseTitle.clear();
//        courseDescription.clear();
//        enrollmentKey.clear();
//        courseCode.clear();
//        createCourseButton.setDisable(true);
//    }




    private void initializeTable(){
        TableColumn<String, Course> column1 = new TableColumn<>("Course ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<String, Course> column2 = new TableColumn<>("Course Code");
        column2.setCellValueFactory(new PropertyValueFactory<>("courseCode"));

        TableColumn<String, Course> column3 = new TableColumn<>("Course Title");
        column3.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));

        TableColumn<String, Course> column5 = new TableColumn<>("Date Added");
        column5.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));

        TableColumn<String, Course> column6 = new TableColumn<>("Date Modified");
        column6.setCellValueFactory(new PropertyValueFactory<>("dateModified"));

        courseTable.getColumns().add(column1);
        courseTable.getColumns().add(column2);
        courseTable.getColumns().add(column3);
        courseTable.getColumns().add(column5);
        courseTable.getColumns().add(column6);
    }

    private void populateTable(){
        Task<Void> getCourseTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                courseList = CourseRest.getCourseByInstructorId(App.instructor.getId());
                return null;
            }
        };

        getCourseTask.setOnSucceeded(e -> {
            courseTable.getItems().clear();
            for(Course course : courseList){
                System.out.println(course.getCourseTitle());
                courseTable.getItems().add(course);
            }
        });

        getCourseTask.setOnFailed(e -> {
            System.out.println(e.getSource().getMessage());
            System.out.println(e.toString());
            System.out.println("failed");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Error in creating the course");
            alert.setContentText("Test");
            alert.showAndWait();
        });

        Thread getCourseThread = new Thread(getCourseTask);
        getCourseThread.setDaemon(true);
        getCourseThread.start();

    }

}
