package com.fozf.jsocc.controllers.dialog;

import com.fozf.jsocc.controllers.StudentDashboardController;
import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.rest.StudentREST;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.ws.rs.core.Response;

public class ConfirmEnrollController {
    @FXML
    private Button enrollButton;

    @FXML
    private PasswordField enrollmentKey;

    private Course courseToEnroll;

    static Response response;

    public StudentDashboardController getStudentDashboardController() {
        return studentDashboardController;
    }

    public void setStudentDashboardController(StudentDashboardController studentDashboardController) {
        this.studentDashboardController = studentDashboardController;
    }

    StudentDashboardController studentDashboardController;

    public EnrollCourseController getEnrollCourseController() {
        return enrollCourseController;
    }

    public void setEnrollCourseController(EnrollCourseController enrollCourseController) {
        this.enrollCourseController = enrollCourseController;
    }

    EnrollCourseController enrollCourseController;

    @FXML
    public void initialize(){
        System.out.println("Confirm Delete initialized");
        enrollButton.setDisable(true);

        enrollmentKey.setOnKeyReleased(this::onInputChanged);

        enrollButton.setOnAction(ev -> {
//            CourseREST.deleteCourse(this.course);
            enrollButton.setDisable(true);

            Task<Void> enrollTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
//                    .deleteCourse(courseToEnroll);
                    response = StudentREST.enrollStudent(App.student.getUsername(), courseToEnroll.getId());
                    return null;
                }
            };

            enrollTask.setOnFailed(event -> {
                enrollButton.setDisable(false);
            });

            enrollTask.setOnSucceeded(event -> {
                System.out.println(response.getStatus());

                if(response.getStatus() == 200){
                    Stage stage = (Stage) enrollButton.getScene().getWindow();
                    stage.close();

                    Stage buttonStage = (Stage) enrollCourseController.enrollButton.getScene().getWindow();
                    buttonStage.close();

                    EnrollCourseController.dashboardController.initializeCoursesTreeView();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("Enrolled Successfully!");
                    alert.setContentText("You are enrolled to " + courseToEnroll.getCourseCode());
                    alert.showAndWait();
                }

            });

            Thread enrollThread = new Thread(enrollTask);
            enrollThread.setDaemon(true);
            enrollThread.start();
        });
    }



    public void setCourse(Course course) {
        this.courseToEnroll = course;
        enrollmentKey.setAccessibleText(course.getCourseTitle());
    }

    private void onInputChanged(KeyEvent event){
        if(enrollmentKey.getText().equals(courseToEnroll.getEnrollmentKey())){
            enrollButton.setDisable(false);
        }else{
            enrollButton.setDisable(true);
        }
    }

}
