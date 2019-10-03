package com.fozf.jsocc.controllers.dialog;

import com.fozf.jsocc.controllers.StudentDashboardController;
import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.ViewBootstrapper;
import com.fozf.jsocc.utils.rest.CourseREST;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnrollCourseController {

    @FXML
    public TableView courseTableView;

    @FXML
    public TextField searchCourse;

    @FXML
    public Button enrollButton;

    static List<Course> courseList;

    public static StudentDashboardController dashboardController;

    public void initialize(){
        initializeTable();
        updateList();
    }

    private void initializeTable(){

        TableColumn<String, Course> column2 = new TableColumn<>("Course Code");
        column2.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        column2.prefWidthProperty().bind(courseTableView.widthProperty().multiply(0.24));

        TableColumn<String, Course> column3 = new TableColumn<>("Course Title");
        column3.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        column3.prefWidthProperty().bind(courseTableView.widthProperty().multiply(0.5));

        TableColumn<String, Course> column4 = new TableColumn<>("Instructor");
        column4.setCellValueFactory(new PropertyValueFactory<>("instructorName"));
        column4.prefWidthProperty().bind(courseTableView.widthProperty().multiply(0.24));

        column2.setResizable(false);
        column3.setResizable(false);
        column4.setResizable(false);

        courseTableView.getColumns().add(column2);
        courseTableView.getColumns().add(column3);
        courseTableView.getColumns().add(column4);

        courseTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        courseTableView.getSelectionModel().selectedItemProperty().addListener(observable -> {
            Course course = (Course) courseTableView.getSelectionModel().getSelectedItem();
            enrollButton.setText("Enroll " + course.getCourseCode());
            enrollButton.setDisable(false);
        });

        searchCourse.setOnKeyReleased(this::searchTable);
        enrollButton.setDisable(true);

        enrollButton.setOnAction(event -> {
            Course courseToEnroll = (Course) courseTableView.getSelectionModel().getSelectedItem();

            try {
                ViewBootstrapper enroll = new ViewBootstrapper("ConfirmEnroll", ViewBootstrapper.Size.CUSTOM_ALERT,  ViewBootstrapper.Type.DIALOG);
                Stage stage = enroll.getStage();
                ConfirmEnrollController controller = enroll.getLoader().getController();
                controller.setCourse(courseToEnroll);
                controller.setEnrollCourseController(this);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                // After the delete update the necessary ui elements
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void searchTable(KeyEvent keyevent){
        ArrayList<Course> resultList = new ArrayList<>();
        String searchQuery = searchCourse.getText().toLowerCase();
        if(courseList.size() != 0){
            for(Course course : courseList){
                if(course.getCourseTitle().toLowerCase().contains(searchQuery) ||
                        course.getCourseCode().toLowerCase().contains(searchQuery) ||
                        course.getInstructorName().toLowerCase().contains(searchQuery)){
                    resultList.add(course);
                }
            }
        }

        if(resultList.size() != 0){
            courseTableView.getItems().clear();
            for(Course course : resultList){
                System.out.println(course.getCourseTitle());
                courseTableView.getItems().add(course);
            }
        }
    }

    public void updateList(){
        Task<Void> getCourseTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                courseList = new ArrayList<>(CourseREST.findAll());
                return null;
            }
        };

        getCourseTask.setOnSucceeded(e -> {
            // Filter course list based on already enrolled course

            ArrayList<Course> copy = new ArrayList<>(courseList);
            List<Long> courseIds = new ArrayList<>();

            for(Course studentCourse : App.student.getCourses()){
                courseIds.add(studentCourse.getId());
            }

            courseList.clear();
            for(Course course : copy){
                if(!courseIds.contains(course.getId())){
                    courseList.add(course);
                }
            }

            System.out.println("Course list content");

            for(Course course : courseList){
                System.out.println(course.getCourseTitle());
            }

            populateTable(courseList);
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

    private void populateTable(List<Course> courses){
        courseTableView.getItems().clear();
        for(Course course :  courses){
            System.out.println(course.getInstructor().getFirstName());
            courseTableView.getItems().add(course);
        }
    }
}
