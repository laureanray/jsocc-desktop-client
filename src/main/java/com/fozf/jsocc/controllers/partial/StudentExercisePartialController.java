package com.fozf.jsocc.controllers.partial;

import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.models.Exercise;
import com.fozf.jsocc.models.ExerciseItem;
import com.fozf.jsocc.utils.ViewBootstrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.beans.EventHandler;
import java.io.IOException;

public class StudentExercisePartialController {
    @FXML
    private Text exerciseTitle, exerciseDescription;

    @FXML
    private ListView<Hyperlink> exerciseItemsListView;

    private Exercise exercise;

    ObservableList<Hyperlink> exerciseItems = FXCollections.observableArrayList();


    public void initialize(){
        System.out.println("Init");
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;

        for(int i = 0; i < exercise.getExerciseItems().size(); i++){
            Hyperlink ex = new Hyperlink(exercise.getExerciseItems().get(i).getItemTitle());

            if(i == 0){
                ex.setDisable(false);
            }else if(i > 0 && exercise.getExerciseItems().get(i-1).isCompleted()){
                ex.setDisable(false);
            }else{
                ex.setDisable(true);
            }

            ex.setId(String.valueOf(exercise.getExerciseItems().get(i).getId()));
            ex.setOnAction(this::onClicked);
            exerciseItems.add(ex);
        }

        initializeUIElements();
        initializeListView();
    }

    private void onClicked(ActionEvent actionEvent) {
        System.out.println(((Hyperlink) actionEvent.getSource()).getId());

        ExerciseItem clickedExerciseItem = new ExerciseItem();

        for(int i = 0; i < exercise.getExerciseItems().size(); i++){
            if(exercise.getExerciseItems().get(i).getId()  == Integer.parseInt((((Hyperlink) actionEvent.getSource()).getId()))){
                clickedExerciseItem = exercise.getExerciseItems().get(i);
            }
        }

        try {
            ViewBootstrapper view = StudentDashboardPartialController.studentDashboardController.changeUI("studentEditorPartial");
            Stage stage = view.getStage();
            StudentEditorPartialController controller = view.getLoader().getController();
            controller.setExerciseItem(clickedExerciseItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initializeUIElements(){
        exerciseTitle.setText(exercise.getExerciseTitle());
        exerciseDescription.setText(exercise.getExerciseDescription());
    }

    public void initializeListView(){
        System.out.println("asdasdasdasd added");
        exerciseItemsListView.setItems(exerciseItems);


    }

}
