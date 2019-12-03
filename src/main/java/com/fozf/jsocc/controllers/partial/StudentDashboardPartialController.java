package com.fozf.jsocc.controllers.partial;
import com.fozf.jsocc.controllers.StudentDashboardController;
import com.fozf.jsocc.utils.App;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class StudentDashboardPartialController {
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox vbox;

    public static StudentDashboardController studentDashboardController;

    @FXML
    public void initialize(){
        App.currentPartial = "studentDashboardPartial";
    }

    public static void setStudentDashboardController(StudentDashboardController studentDashboardController) {
        StudentDashboardPartialController.studentDashboardController = studentDashboardController;
    }
}
