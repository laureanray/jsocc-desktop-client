package com.fozf.jsocc.controllers.partial;

import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.models.Exercise;
import com.fozf.jsocc.utils.ViewBootstrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentCoursePartialController {

    @FXML
    private Text courseTitle, courseDescription;

    @FXML
    private ListView<Hyperlink> exercisesListView;

    private Course course;

    ObservableList<Hyperlink> exercises = FXCollections.observableArrayList();

    public void initialize(){
        System.out.println("Init");
    }

    public void setCourse(Course course) {
        this.course = course;

        List<Exercise> er = course.getExercises();

        er.sort(Comparator.comparing(Exercise::getId));

        for(int i = 0; i < er.size(); i++){
            System.out.println(er.get(i).getExerciseTitle());

            Hyperlink ex = new Hyperlink(er.get(i).getExerciseTitle());
            ex.setId(String.valueOf(er.get(i).getId()));

            if(i == 0){
                ex.setDisable(false);
            }else if(i > 0 && er.get(i-1).isCompleted()){
                ex.setDisable(false);
            }else{
                ex.setDisable(true);
            }

            ex.setOnAction(this::onClicked);
            exercises.add(ex);


        }

        initializeUIElements();
        initializeListView();
    }

    private void onClicked(ActionEvent actionEvent) {
        System.out.println(((Hyperlink) actionEvent.getSource()).getId());

        Exercise clickedExercise = new Exercise();

        for(int i = 0; i < course.getExercises().size(); i++){
            if(course.getExercises().get(i).getId()  == Integer.parseInt((((Hyperlink) actionEvent.getSource()).getId()))){
                clickedExercise = course.getExercises().get(i);
            }
        }

        try {
            ViewBootstrapper view = StudentDashboardPartialController.studentDashboardController.changeUI("studentExercisePartial");
            Stage stage = view.getStage();
            StudentExercisePartialController controller = view.getLoader().getController();
            controller.setExercise(clickedExercise);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeUIElements(){
        courseTitle.setText(course.getCourseTitle());
        courseDescription.setText(course.getCourseDescription());
    }

    public void initializeListView(){
        exercisesListView.setItems(exercises);
    }
}
