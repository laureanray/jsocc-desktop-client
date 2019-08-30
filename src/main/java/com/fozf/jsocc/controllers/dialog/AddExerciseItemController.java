package com.fozf.jsocc.controllers.dialog;

import com.fozf.jsocc.controllers.partial.InstructorCoursePartialController;
import com.fozf.jsocc.models.Exercise;
import com.fozf.jsocc.models.ExerciseItem;
import com.fozf.jsocc.models.TestCase;
import com.fozf.jsocc.utils.ViewBootstrapper;
import com.fozf.jsocc.utils.error.ErrorREST;
import com.fozf.jsocc.utils.rest.ExerciseItemREST;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.text.View;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class AddExerciseItemController {
    @FXML
    private TextField itemTitle, itemDescription, points;
    @FXML
    private Button addTestCaseButton, addExerciseItemButton, cancelButton;
    @FXML
    private TableView testCasesTable;
    private InstructorCoursePartialController controller;

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    private Exercise exercise;
    private ObservableList<TestCase> testCases = FXCollections.observableArrayList();

    public void setController(InstructorCoursePartialController controller) {
        this.controller = controller;
        attachEventListeners();
    }

    static Response response;

    private void attachEventListeners() {
        addExerciseItemButton.setOnAction(event -> {
            addExerciseItemButton.setDisable(true);
            ExerciseItem exerciseItem = new ExerciseItem();
            exerciseItem.setItemTitle(itemTitle.getText());
            exerciseItem.setExercise(exercise);
            for(TestCase testCase : testCases){
                exerciseItem.getTestCases().add(testCase);
            }


            Task<Void> addExerciseItemTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    response = ExerciseItemREST.addExerciseItem(exerciseItem);
                    return null;
                }
            };

            addExerciseItemTask.setOnSucceeded(ev -> {
                Stage stage = (Stage) addExerciseItemButton.getScene().getWindow();
                stage.close();
            });

            addExerciseItemTask.setOnFailed(ev -> {
                new ErrorREST("Test").getAlert().showAndWait();
            });

            Thread addExerciseItemThread = new Thread(addExerciseItemTask);
            addExerciseItemThread.setDaemon(true);
            addExerciseItemThread.start();
        });

        addTestCaseButton.setOnAction(event -> {
            try {
                ViewBootstrapper view = new ViewBootstrapper("AddTestCase", ViewBootstrapper.Size.CUSTOM_ALERT_SM, ViewBootstrapper.Type.DIALOG);
                AddTestCaseController controller = view.getLoader().getController();
                controller.setController(this);
                Stage stage = view.getStage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    @FXML
    public void initialize(){
        addExerciseItemButton.setDisable(true);
        initializeTable();
    }


    private void initializeTable(){
        TableColumn<String, TestCase> column2 = new TableColumn<>("Input");
        column2.setCellValueFactory(new PropertyValueFactory<>("input"));
        column2.prefWidthProperty().bind(testCasesTable.widthProperty().multiply(0.15));

        TableColumn<String, TestCase> column3 = new TableColumn<>("Output");
        column3.setCellValueFactory(new PropertyValueFactory<>("output"));
        column3.prefWidthProperty().bind(testCasesTable.widthProperty().multiply(0.3));

        TableColumn<String, TestCase> column4 = new TableColumn<>("Hidden");
        column4.setCellValueFactory(new PropertyValueFactory<>("output"));
        column4.prefWidthProperty().bind(testCasesTable.widthProperty().multiply(0.2));

        column2.setResizable(false);
        column3.setResizable(false);
        column4.setResizable(false);

        testCasesTable.getColumns().add(column2);
        testCasesTable.getColumns().add(column3);
        testCasesTable.getColumns().add(column4);
        testCasesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        testCasesTable.setItems(testCases);
    }

    public boolean addTestCase(TestCase testCase){
        boolean state =  testCases.add(testCase);
        checkInputs();
        return state;
    }
    private void checkInputs(){
        if(testCases.size() > 0 &&
           !itemTitle.getText().isEmpty() &&
           !itemDescription.getText().isEmpty() &&
           !points.getText().isEmpty()){
            addExerciseItemButton.setDisable(false);
        }
    }

}
