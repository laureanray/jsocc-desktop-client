package com.fozf.jsocc.controllers.dialog;

import com.fozf.jsocc.controllers.partial.InstructorCoursePartialController;
import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.models.Exercise;
import com.fozf.jsocc.utils.rest.ExerciseREST;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.ws.rs.core.Response;
import java.sql.Date;

public class AddExerciseController {
    private InstructorCoursePartialController controller;

    public void setController(InstructorCoursePartialController controller) {
        this.controller = controller;
    }

    @FXML
    private Button addExerciseButton;
    @FXML
    private TextField exerciseTitle;
    @FXML
    private DatePicker exerciseDeadline;

    static Response response;

    private Course course;

    public void setCourse(Course course) {
        this.course = course;
        attachEventListener();
    }

    @FXML
    public void initialize(){
        addExerciseButton.setDisable(true);
        exerciseDeadline.setOnAction(this::onChange);
        exerciseTitle.setOnKeyReleased(this::onChange);
    }

    private void onChange(Event e){
        if(exerciseTitle.getText().length() > 0 && exerciseDeadline.getValue() != null) addExerciseButton.setDisable(false);
    }

    private void attachEventListener(){
        addExerciseButton.setOnAction(event -> {
            addExerciseButton.setDisable(true);
            Task<Void> addExerciseTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Exercise exercise = new Exercise();
                    exercise.setExerciseTitle(exerciseTitle.getText());
                    exercise.setExerciseDeadline(Date.valueOf(exerciseDeadline.getValue()));
                    exercise.setCourse(course);
                    response = ExerciseREST.addExercise(exercise);
                    return null;
                }
            };

            addExerciseTask.setOnSucceeded(ev -> {
                addExerciseButton.setDisable(false);
                Stage stage = (Stage) addExerciseButton.getScene().getWindow();
                // update the table before closing
                if(controller != null){
                    controller.getExercises();
                }
                stage.close();
            });

            addExerciseTask.setOnFailed(ev -> {
                addExerciseButton.setDisable(false);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("error saving exercise");
                alert.setContentText("errorasdad");
            });

            Thread addExerciseThread = new Thread(addExerciseTask);
            addExerciseThread.setDaemon(true);

            addExerciseThread.start();
        });
    }


}
