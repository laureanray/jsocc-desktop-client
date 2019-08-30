package com.fozf.jsocc.controllers.partial;

import com.fozf.jsocc.controllers.CreateCourseController;
import com.fozf.jsocc.controllers.InstructorDashboardController;
import com.fozf.jsocc.controllers.dialog.ConfirmDeleteController;
import com.fozf.jsocc.models.Course;
import com.fozf.jsocc.utils.App;
import com.fozf.jsocc.utils.rest.CourseREST;
import com.fozf.jsocc.utils.ViewBootstrapper;
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


public class InstructorCoursesPartialController {
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


    private Stage stage;

    private List<Course> courseList = new ArrayList<>();
    private ViewBootstrapper createCourseView = null;

    private InstructorDashboardController dashboardController;

    @FXML
    public void initialize(){
        // Update table
        System.out.println("Initialize Partial");
        initializeTable();
        updateTableAsync();
        attachEventListeners();

        try {
            createCourseView = new ViewBootstrapper("CourseCreate", ViewBootstrapper.Size.SMALL,  ViewBootstrapper.Type.NORMAL);
            stage  = createCourseView.getStage();
            stage.setOnCloseRequest(ev -> stage.close());
            CreateCourseController controller = createCourseView.getLoader().getController();
            controller.setController(this);
            stage.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Unable to load create new courses");
        }

        createNewCourseButton.setOnAction(e -> {
            stage.showAndWait();
        });

        searchTextField.setOnKeyReleased(this::searchTable);
    }

    private void searchTable(KeyEvent keyEvent) {
        if(keyEvent.getCode() != KeyCode.ENTER){
            // if not enter query the table
            ArrayList<Course> resultList = new ArrayList<>();
            String searchQuery = searchTextField.getText();
            if(courseList.size() != 0){
                for(Course course : courseList){
                    if(course.getCourseTitle().contains(searchQuery) ||
                       course.getCourseCode().contains(searchQuery) ||
                       course.getCourseDescription().contains(searchQuery)){
                        resultList.add(course);
                    }
                }
            }

            if(resultList.size() != 0){
                courseTable.getItems().clear();
                for(Course course : resultList){
                    System.out.println(course.getCourseTitle());
                    courseTable.getItems().add(course);
                }
            }
        }
    }

    private void initializeTable(){
        TableColumn<String, Course> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column1.prefWidthProperty().bind(courseTable.widthProperty().multiply(0.1));

        TableColumn<String, Course> column2 = new TableColumn<>("Course Code");
        column2.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        column2.prefWidthProperty().bind(courseTable.widthProperty().multiply(0.15));

        TableColumn<String, Course> column3 = new TableColumn<>("Course Title");
        column3.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        column3.prefWidthProperty().bind(courseTable.widthProperty().multiply(0.3));

        TableColumn<String, Course> column4 = new TableColumn<>("Date Added");
        column4.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        column4.prefWidthProperty().bind(courseTable.widthProperty().multiply(0.2));

        TableColumn<String, Course> column5 = new TableColumn<>("Date Modified");
        column5.setCellValueFactory(new PropertyValueFactory<>("dateModified"));
        column5.prefWidthProperty().bind(courseTable.widthProperty().multiply(0.2));

        column1.setResizable(false);
        column2.setResizable(false);
        column3.setResizable(false);
        column4.setResizable(false);
        column5.setResizable(false);

        courseTable.getColumns().add(column1);
        courseTable.getColumns().add(column2);
        courseTable.getColumns().add(column3);
        courseTable.getColumns().add(column4);
        courseTable.getColumns().add(column5);

        //  Set selection mode
        courseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void updateTableAsync(){

        Task<Void> getCourseTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                courseList = CourseREST.findByInstructorId(App.instructor.getId());
                return null;
            }
        };

        getCourseTask.setOnSucceeded(e -> {
            populateTable(courseList);
        });

        getCourseTask.setOnFailed(e -> {
            System.out.println(e.getSource().getMessage());
            System.out.println(e.toString());
            System.out.println("failed");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Error in creating the course");
            alert.setContentText("Test");
            alert.showAndWait();
        });

        Thread getCourseThread = new Thread(getCourseTask);
        getCourseThread.setDaemon(true);
        getCourseThread.start();
    }

    public void setDashboardController(InstructorDashboardController dashboardController) {
        this.dashboardController = dashboardController;
        System.out.println("Dashboard controller set");
    }

    private void populateTable(List<Course> courses){
        courseTable.getItems().clear();
        for(Course course :  courses){
            System.out.println(course.getCourseTitle());
            courseTable.getItems().add(course);
        }
    }

    private void attachEventListeners(){
        ContextMenu contextMenuSingle = new ContextMenu();
        MenuItem item1 = new MenuItem("Open");
        MenuItem item2 = new MenuItem("Add Exercise");
        MenuItem item3 = new MenuItem("Edit");
        MenuItem item4 = new MenuItem("Delete");

        contextMenuSingle.getItems().add(item1);
        contextMenuSingle.getItems().add(item2);
        contextMenuSingle.getItems().add(item3);
        contextMenuSingle.getItems().add(item4);

        ContextMenu contextMenuMultiple = new ContextMenu();
        MenuItem menuMultiple1 = new MenuItem("Delete selected items");

        menuMultiple1.setOnAction(ev -> {
            System.out.println(courseTable.getSelectionModel().getSelectedItems().size());
        });

        contextMenuMultiple.getItems().add(menuMultiple1);

        item1.setOnAction(ev -> {
            contextMenuSingle.hide();
            System.out.println("Open Clicked");
            Course selectedCourse = (Course) courseTable.getSelectionModel().getSelectedItem();
            System.out.println(selectedCourse.getId());

            try {
                ViewBootstrapper view = this.dashboardController.changeUI("instructorCoursePartial");
                InstructorCoursePartialController controller = view.getLoader().getController();
                controller.setCourse(selectedCourse);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        item2.setOnAction(ev -> {
            System.out.println("Add Exercise");
            contextMenuSingle.hide();

        });

        item3.setOnAction(ev -> {
            System.out.println("Edit");
            contextMenuSingle.hide();

        });

        item4.setOnAction(ev -> {
            contextMenuSingle.hide();

            deleteSelectedCourse(contextMenuSingle);
        });


        courseTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.SECONDARY){
                if(courseTable.getSelectionModel().getSelectedItems().size() > 1){
                    contextMenuMultiple.show(courseTable, event.getScreenX(), event.getScreenY());
                }else{
                    contextMenuSingle.show(courseTable, event.getScreenX(), event.getScreenY());
                }
            }else{
                // This hides the context menu if clicked outside
                contextMenuMultiple.hide();
                contextMenuSingle.hide();
            }

            if(event.getClickCount() == 2){
                item1.fire();
            }
        });



        courseTable.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            System.out.println("FIRE");
            if(event.getCode() == KeyCode.DELETE){
                item4.fire();
            } else if(event.getCode() == KeyCode.ENTER){
                if(courseTable.getSelectionModel().getSelectedItems().size() == 1){
                    item1.fire();
                }
            } else{
                System.out.println(event.getCode());
            }
        });
    }

    private void deleteSelectedCourse(ContextMenu contextMenu){
        Course selectedCourse = (Course) courseTable.getSelectionModel().getSelectedItem();
        System.out.println("Delete");
        contextMenu.hide();
        try {
            ViewBootstrapper delete = new ViewBootstrapper("ConfirmDelete", ViewBootstrapper.Size.CUSTOM_ALERT,  ViewBootstrapper.Type.DIALOG);
            Stage stage = delete.getStage();
            ConfirmDeleteController controller = delete.getLoader().getController();
            controller.setCourse(selectedCourse);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            // After the delete update the table
            updateTableAsync();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
