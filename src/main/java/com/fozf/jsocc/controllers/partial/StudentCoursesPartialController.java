package com.fozf.jsocc.controllers.partial;

import com.fozf.jsocc.controllers.CreateCourseController;
import com.fozf.jsocc.controllers.InstructorDashboardController;
import com.fozf.jsocc.controllers.StudentDashboardController;
import com.fozf.jsocc.controllers.dialog.AddExerciseController;
import com.fozf.jsocc.controllers.dialog.ConfirmDeleteController;
import com.fozf.jsocc.controllers.dialog.EnrollCourseController;
import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.ViewBootstrapper;
import com.fozf.jsocc.utils.rest.CourseREST;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StudentCoursesPartialController {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vbox;
    @FXML
    private Button createNewCourseButton, createCourseButton, cancelButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView courseTable, exerciseItemsTable;
    @FXML
    private Text exerciseTitle;
    @FXML
    private Text tableTitle;
    @FXML
    private Button enrollButton;



    private Stage stage;

    private List<Course> courseList = new ArrayList<>();
    private ViewBootstrapper createCourseView = null;

    private StudentDashboardController dashboardController;

    @FXML
    public void initialize(){

        App.currentPartial = "studentCoursePartial";

        // Add event listeners
        enrollButton.setOnAction(evt -> {
            try {
                ViewBootstrapper delete = new ViewBootstrapper("EnrollCourse", ViewBootstrapper.Size.SMALL,  ViewBootstrapper.Type.DIALOG);
                Stage stage = delete.getStage();
                EnrollCourseController.dashboardController = dashboardController;
//                AddExerciseController controller = delete.getLoader().getController();
//                controller.setController(this);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    public StudentDashboardController getDashboardController() {
        return dashboardController;
    }

    public void setDashboardController(StudentDashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
}
