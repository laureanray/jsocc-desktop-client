package com.fozf.jsocc.controllers.partial;

import com.fozf.jsocc.models.Course;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class InstructorCoursePartialController {
    @FXML
    private Text courseTitle, courseDescription;
    @FXML
    private TextFlow textFlow;
    @FXML
    private TableView exerciseTableView;
    private Course course;

    @FXML
    public void initialize(){

    }

    public void setCourse(Course course) {
        this.course = course;
        initializeUI();
        initializeTable();
    }

    private void initializeTable(){
        TableColumn<String, Course> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column1.prefWidthProperty().bind(exerciseTableView.widthProperty().multiply(0.1));

        TableColumn<String, Course> column2 = new TableColumn<>("Course Code");
        column2.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        column2.prefWidthProperty().bind(exerciseTableView.widthProperty().multiply(0.15));

        TableColumn<String, Course> column3 = new TableColumn<>("Course Title");
        column3.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        column3.prefWidthProperty().bind(exerciseTableView.widthProperty().multiply(0.3));

        TableColumn<String, Course> column4 = new TableColumn<>("Date Added");
        column4.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        column4.prefWidthProperty().bind(exerciseTableView.widthProperty().multiply(0.2));

        TableColumn<String, Course> column5 = new TableColumn<>("Date Modified");
        column5.setCellValueFactory(new PropertyValueFactory<>("dateModified"));
        column5.prefWidthProperty().bind(exerciseTableView.widthProperty().multiply(0.2));

        column1.setResizable(false);
        column2.setResizable(false);
        column3.setResizable(false);
        column4.setResizable(false);
        column5.setResizable(false);

        exerciseTableView.getColumns().add(column1);
        exerciseTableView.getColumns().add(column2);
        exerciseTableView.getColumns().add(column3);
        exerciseTableView.getColumns().add(column4);
        exerciseTableView.getColumns().add(column5);

        exerciseTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void initializeUI(){
        courseTitle.setText(course.getCourseTitle());
        courseDescription.setText(course.getCourseDescription());
    }
}
