package com.fozf.jsocc.controllers;

import com.fozf.jsocc.controllers.partial.InstructorCoursePartialController;
import com.fozf.jsocc.controllers.partial.InstructorCoursesPartialController;
import com.fozf.jsocc.controllers.partial.StudentCoursesPartialController;
import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.ViewBootstrapper;
import com.fozf.jsocc.utils.rest.CourseREST;
import com.fozf.jsocc.utils.rest.StudentREST;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDashboardController {
    private Stage stage;


    @FXML
    public MenuButton accountMenu;

    @FXML
    public MenuItem logoutMenuItem;

    @FXML
    public Hyperlink dashboardLink;

    @FXML
    public TreeView<String> coursesTreeView;

    @FXML
    public TitledPane coursesTitledPane;

    @FXML
    public BorderPane rootPane;


    public StudentDashboardController(){
        if(App.student == null || !App.isStudent){
            throw new RuntimeException("Must be student to show this dashboard.");
        }
    }

    @FXML
    public void initialize(){

        System.out.println("Login student: ");
        System.out.println(App.student.getFirstName());
        accountMenu.setText(App.student.getFirstName() + " " + App.student.getLastName());
        logoutMenuItem.setOnAction(this::showLogoutDialog);


        dashboardLink.setOnAction(e -> {
            try {
                ViewBootstrapper view = changeUI("instructorDashboardPartial");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        dashboardLink.fire();

        coursesTitledPane.addEventHandler(MouseEvent.MOUSE_CLICKED, ev -> {
            // Load the partial if not loaded in the scene.
            if(!App.currentPartial.equals("instructorCoursesPartial")){
                try {
                    ViewBootstrapper view = changeUI("studentCoursesPartial");
                    StudentCoursesPartialController controller = view.getLoader().getController();
                    controller.setDashboardController(this);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


        coursesTreeView.setOnMouseClicked(click -> {

            if (click.getClickCount() == 2) {
                System.out.println("Double clicked: " + coursesTreeView.getSelectionModel().getSelectedItem().getValue());

//                for(Course course : courseList){
//                    if(course.getCourseTitle().equals(coursesTreeView.getSelectionModel().getSelectedItem().getValue())){
//                        // s
//                        System.out.println("tada");
//                        System.out.println(course.getId());
//
//                        try {
//                            ViewBootstrapper view = this.changeUI("instructorCoursePartial");
//                            InstructorCoursePartialController controller = view.getLoader().getController();
//                            controller.setCourse(course);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }
            }

        });

        initializeCoursesTreeView();

    }

    void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(e -> {
            System.out.println("Closing");
            showLogoutDialog(e);
        });
    }

    private void showLogoutDialog(Event e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Logging out.");
        alert.setContentText("Are you sure you want to do this? ");
        Optional<ButtonType> result  = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            try {
                new ViewBootstrapper("Login", ViewBootstrapper.Size.LOGIN, ViewBootstrapper.Type.NORMAL).getStage().show();
                App.instructor = null;
                this.stage.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }else{
            e.consume();
        }


    }

    public ViewBootstrapper changeUI(String filename) throws IOException {

        ViewBootstrapper view = new ViewBootstrapper(filename, ViewBootstrapper.Size.INSIDE,  ViewBootstrapper.Type.PARTIAL);
        Node root = view.getLoader().getRoot();
        rootPane.setCenter(root);

        root.opacityProperty().set(0);
        Timeline timeline = new Timeline();

        List<KeyValue> keyValues = new ArrayList<>();
        KeyValue kv2= new KeyValue(root.opacityProperty(), 1, Interpolator.EASE_IN);

        KeyFrame kf2 = new KeyFrame(Duration.seconds(0.2), kv2);
        timeline.getKeyFrames().add(kf2);

        timeline.play();

        return view;
    }

    public void initializeCoursesTreeView(){
        System.out.println("COurses tree viwe");
        TreeItem<String> rootItem = new TreeItem<String>();
        rootItem.setExpanded(true);

        Node javaItemIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/java.png")));
        TreeItem<String> javaItem = new TreeItem<>("Java Courses", javaItemIcon);
        Node pythonItemIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/python.png")));
        TreeItem<String> pythonItem = new TreeItem<>("Python Course", pythonItemIcon);

        rootItem.getChildren().add(javaItem);
        rootItem.getChildren().add(pythonItem);

        // Update users data here?
        Task<Void> updateUserDataTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("trying.. "+ App.student.getUsername()   );
                App.student = StudentREST.getStudentByUsername(App.student.getUsername().trim());
                return null;
            }
        };

        Thread updateUserDataThread = new Thread(updateUserDataTask);

        updateUserDataTask.setOnSucceeded(ev -> {
            System.out.println("Students courses");
            for(Course course : App.student.getCourses()){
                TreeItem<String> item = new TreeItem<>(course.getCourseTitle());
                item.getChildren().add(new TreeItem<>("Test"));

                if(course.getCourseProgrammingLanguage().equals("Java")){
                    javaItem.getChildren().add(item);
                }else{
                    pythonItem.getChildren().add(item);
                }
            }
        });

        updateUserDataTask.setOnFailed(e -> {
            System.out.println(e.getSource().getMessage());
            System.out.println(e.getEventType().getName());
            System.out.println("failed");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Error in creating the course");
            alert.setContentText("Test");
            alert.showAndWait();
        });

        updateUserDataThread.setDaemon(true);
        updateUserDataThread.start();
//        coursesTitledPane.addEventHandler(MouseEvent.MOUSE_CLICKED, ev -> {
//            // Load the partial if not loaded in the scene.
//            if(!App.currentPartial.equals("instructorCoursesPartial")){
//                try {
//                    ViewBootstrapper view = changeUI("instructorCoursesPartial");
//                    InstructorCoursesPartialController controller = view.getLoader().getController();
//                    controller.setDashboardController(this);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//
//
//        coursesTreeView.setOnMouseClicked(click -> {
//
//            if (click.getClickCount() == 2) {
//                System.out.println("Double clicked: " + coursesTreeView.getSelectionModel().getSelectedItem().getValue());
//
//                for(Course course : courseList){
//                    if(course.getCourseTitle().equals(coursesTreeView.getSelectionModel().getSelectedItem().getValue())){
//                        // s
//                        System.out.println("tada");
//                        System.out.println(course.getId());
//
//                        try {
//                            ViewBootstrapper view = this.changeUI("instructorCoursePartial");
//                            InstructorCoursePartialController controller = view.getLoader().getController();
//                            controller.setCourse(course);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }
//            }

//        });
//        coursesTreeView.setShowRoot(false);
//        coursesTreeView.setRoot(rootItem);

        coursesTreeView.setShowRoot(false);
        coursesTreeView.setRoot(rootItem);

    }


}
