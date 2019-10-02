package com.fozf.jsocc.controllers.partial;

import com.fozf.jsocc.controllers.dialog.AddExerciseController;
import com.fozf.jsocc.controllers.dialog.AddExerciseItemController;
import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.models.Exercise;
import com.fozf.jsocc.models.ExerciseItem;
import com.fozf.jsocc.utils.App;
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
    private Text courseTitle, courseDescription, exerciseTitle;
    @FXML
    private TextFlow textFlow;
    private Course course;

    @FXML
    private Button addExerciseButton, addAssignmentButton, addMaterialButton, addExerciseItemButton;

    @FXML
    private TableView exercisesTableView, exerciseItemsTable;

    private List<Exercise> exercises;

    private Exercise selectedExercise;

    @FXML
    public void initialize(){

        initializeTables();
        attachEventListeners();

        addExerciseButton.setOnAction(event -> {
            try {
                ViewBootstrapper delete = new ViewBootstrapper("AddExercise", ViewBootstrapper.Size.CUSTOM_ALERT_SM,  ViewBootstrapper.Type.DIALOG);
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

        addExerciseItemButton.setOnAction(event -> {
            try {
                ViewBootstrapper delete = new ViewBootstrapper("AddExerciseItem", ViewBootstrapper.Size.SMALL,  ViewBootstrapper.Type.DIALOG);
                Stage stage = delete.getStage();
                AddExerciseItemController controller = delete.getLoader().getController();
                if(selectedExercise != null){
                    controller.setExercise(selectedExercise);
                }
                controller.setController(this);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void attachEventListeners() {
        exercisesTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                Exercise selected = (Exercise) exercisesTableView.getSelectionModel().getSelectedItem();
                changeExercise(selected);
            }
        });

    }

    private void changeExercise(Exercise selected) {
        exerciseTitle.setText(selected.getExerciseTitle());
        loadListIntoExerciseItemsTable(selected.getExerciseItems());
        selectedExercise = selected;
    }

    public void setCourse(Course course) {
        this.course = course;
        initializeUI();
    }

    private void initializeTables(){
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

        TableColumn<String, ExerciseItem> col1 = new TableColumn<>("ID");
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col1.prefWidthProperty().bind(exerciseItemsTable.widthProperty().multiply(0.1));

        TableColumn<String, ExerciseItem> col2 = new TableColumn<>("Title");
        col2.setCellValueFactory(new PropertyValueFactory<>("itemTitle"));
        col2.prefWidthProperty().bind(exerciseItemsTable.widthProperty().multiply(0.15));

        TableColumn<String, ExerciseItem> col3 = new TableColumn<>("Description");
        col3.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        col3.prefWidthProperty().bind(exerciseItemsTable.widthProperty().multiply(0.3));

        TableColumn<String, ExerciseItem> col4 = new TableColumn<>("Points");
        col4.setCellValueFactory(new PropertyValueFactory<>("ppomts"));
        col4.prefWidthProperty().bind(exerciseItemsTable.widthProperty().multiply(0.2));


        col1.setResizable(false);
        col2.setResizable(false);
        col3.setResizable(false);
        col4.setResizable(false);

        exerciseItemsTable.getColumns().add(col1);
        exerciseItemsTable.getColumns().add(col2);
        exerciseItemsTable.getColumns().add(col3);
        exerciseItemsTable.getColumns().add(col4);
        exerciseItemsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void loadListIntoExerciseTable(List<Exercise> exercises){
        exercisesTableView.getItems().clear();
        for(Exercise exercise : exercises){
            exercisesTableView.getItems().add(exercise);
        }
    }

    private void loadListIntoExerciseItemsTable(List<ExerciseItem> exerciseItems){
        exerciseItemsTable.getItems().clear();
        for(ExerciseItem exerciseItem : exerciseItems){
            exerciseItemsTable.getItems().add(exerciseItem);
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
                loadListIntoExerciseTable(exercises);
            }
        });

        getExercisesTask.setOnFailed(event -> {
            new ErrorREST("event").getAlert().showAndWait();
        });
    }
}
