package com.fozf.jsocc.controllers.partial;

import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.CourseREST;
import com.fozf.jsocc.utils.ViewBootstrap;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class InstructorCoursesPartialController {
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox vbox;
    @FXML
    public Button createNewCourseButton, createCourseButton, cancelButton;
    @FXML
    public TextField searchTextField;
//    @FXML
//    public VBox courseForm;
    @FXML
    public TableView courseTable;
//    @FXML
//    public TextField courseTitle, enrollmentKey, courseCode;
//    @FXML
//    blic TextArea courseDescription;

    private List<Course> courseList = new ArrayList<>();
    private ViewBootstrap createCourseView = null;
    @FXML
    public void initialize(){
        // Udpate table
        System.out.println("Initialize Partial");
        initializeTable();
        populateTable();

        try {
            createCourseView = new ViewBootstrap("CreateCourse", ViewBootstrap.Size.SMALL);
        } catch (IOException e){
            System.out.println("Unable to load create new courses");
        }


//        courseForm.setVisible(false);
        // Add event listener
        createNewCourseButton.setOnAction(e -> {
            Stage stage  = createCourseView.getStage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        });
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
        searchTextField.setOnKeyReleased(this::searchTable);
    }

    private void searchTable(KeyEvent keyEvent) {
        if(keyEvent.getCode() != KeyCode.ENTER){
            // if not enter query the table
            ArrayList<Course> resultList = new ArrayList<>();
            String searchQuery = searchTextField.getText();
            if(courseList.size() != 0){
                for(Course course : courseList){
                    if(course.getCourseTitle().contains(searchQuery) ||
                       course.getCourseCode().contains(searchQuery) ||
                       course.getCourseDescription().contains(searchQuery)){
                        resultList.add(course);
                    }
                }
            }

            if(resultList.size() != 0){
                courseTable.getItems().clear();
                for(Course course : resultList){
                    System.out.println(course.getCourseTitle());
                    courseTable.getItems().add(course);
                }
            }
        }
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
        TableColumn<String, Course> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column1.prefWidthProperty().bind(courseTable.widthProperty().multiply(0.1));

        TableColumn<String, Course> column2 = new TableColumn<>("Course Code");
        column2.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        column2.prefWidthProperty().bind(courseTable.widthProperty().multiply(0.15));

        TableColumn<String, Course> column3 = new TableColumn<>("Course Title");
        column3.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        column3.prefWidthProperty().bind(courseTable.widthProperty().multiply(0.3));

        TableColumn<String, Course> column4 = new TableColumn<>("Date Added");
        column4.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        column4.prefWidthProperty().bind(courseTable.widthProperty().multiply(0.2));

        TableColumn<String, Course> column5 = new TableColumn<>("Date Modified");
        column5.setCellValueFactory(new PropertyValueFactory<>("dateModified"));
        column5.prefWidthProperty().bind(courseTable.widthProperty().multiply(0.2));

        column1.setResizable(false);
        column2.setResizable(false);
        column3.setResizable(false);
        column4.setResizable(false);
        column5.setResizable(false);

        courseTable.getColumns().add(column1);
        courseTable.getColumns().add(column2);
        courseTable.getColumns().add(column3);
        courseTable.getColumns().add(column4);
        courseTable.getColumns().add(column5);

        //  Set selection moed
        courseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void populateTable(){



        Task<Void> getCourseTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                courseList = CourseREST.findByInstructorId(App.instructor.getId());
                return null;
            }
        };

        getCourseTask.setOnSucceeded(e -> {
            courseTable.getItems().clear();
            for(Course course : courseList){
                System.out.println(course.getCourseTitle());
                courseTable.getItems().add(course);
            }

            ContextMenu contextMenu = new ContextMenu();
            MenuItem item1 = new MenuItem("Edit");
            contextMenu.getItems().add(item1);

            courseTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getButton() == MouseButton.SECONDARY){
                        contextMenu.show(courseTable, event.getScreenX(), event.getScreenY());
                    }else{
                        contextMenu.hide();
                    }
                }
            });
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
