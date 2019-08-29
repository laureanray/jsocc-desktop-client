package com.fozf.jsocc.controllers.partial;

import com.fozf.jsocc.controllers.dialog.AddExerciseController;
import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.models.Exercise;
import com.fozf.jsocc.utils.ViewBootstrapper;
import com.fozf.jsocc.utils.error.ErrorREST;
import com.fozf.jsocc.utils.rest.ExerciseREST;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class InstructorCoursePartialController {
    @FXML
    private Text courseTitle, courseDescription;
    @FXML
    private TextFlow textFlow;
    private Course course;

    @FXML
    private Button addExerciseButton, addAssignmentButton, addMaterialButton;

    @FXML
    private TableView exercisesTableView;

    private List<Exercise> exercises;

    @FXML
    public void initialize(){
        initializeTable();

        addExerciseButton.setOnAction(event -> {
            try {
                ViewBootstrapper delete = new ViewBootstrapper("AddExercise", ViewBootstrapper.Size.CUSTOMER_ALERT);
                Stage stage = delete.getStage();
                AddExerciseController controller = delete.getLoader().getController();
                controller.setCourse(course);
                controller.setController(this);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void setCourse(Course course) {
        this.course = course;
        initializeUI();
    }

    private void initializeTable(){
        TableColumn<String, Exercise> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column1.prefWidthProperty().bind(exercisesTableView.widthProperty().multiply(0.1));

        TableColumn<String, Exercise> column2 = new TableColumn<>("Exercise Title");
        column2.setCellValueFactory(new PropertyValueFactory<>("exerciseTitle"));
        column2.prefWidthProperty().bind(exercisesTableView.widthProperty().multiply(0.15));

        TableColumn<String, Exercise> column3 = new TableColumn<>("Exercise Deadline");
        column3.setCellValueFactory(new PropertyValueFactory<>("exerciseDeadline"));
        column3.prefWidthProperty().bind(exercisesTableView.widthProperty().multiply(0.3));

        TableColumn<String, Exercise> column4 = new TableColumn<>("Date Added");
        column4.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        column4.prefWidthProperty().bind(exercisesTableView.widthProperty().multiply(0.2));

        TableColumn<String, Exercise> column5 = new TableColumn<>("Date Modified");
        column5.setCellValueFactory(new PropertyValueFactory<>("dateModified"));
        column5.prefWidthProperty().bind(exercisesTableView.widthProperty().multiply(0.2));

        column1.setResizable(false);
        column2.setResizable(false);
        column3.setResizable(false);
        column4.setResizable(false);
        column5.setResizable(false);

        exercisesTableView.getColumns().add(column1);
        exercisesTableView.getColumns().add(column2);
        exercisesTableView.getColumns().add(column3);
        exercisesTableView.getColumns().add(column4);
        exercisesTableView.getColumns().add(column5);
        exercisesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void loadListIntoTable(List<Exercise> exercises){
        for(Exercise exercise : exercises){
            exercisesTableView.getItems().add(exercise);
        }
    }

    private void initializeUI(){
        courseTitle.setText(course.getCourseTitle());
        courseDescription.setText(course.getCourseDescription());
        getExercises();
    }

    public void getExercises(){
        Task<Void> getExercisesTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                exercises = ExerciseREST.getExercisesUsingCourseId(course.getId());
                return null;
            }
        };

        Thread getExercisesThread = new Thread(getExercisesTask);
        getExercisesThread.setDaemon(true);
        getExercisesThread.start();

        getExercisesTask.setOnSucceeded(event -> {
            if(exercises != null){
                loadListIntoTable(exercises);
            }
        });

        getExercisesTask.setOnFailed(event -> {
            new ErrorREST("event").getAlert().showAndWait();
        });
    }
}
