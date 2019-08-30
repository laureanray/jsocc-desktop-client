package com.fozf.jsocc.controllers.dialog;

import com.fozf.jsocc.models.TestCase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class AddTestCaseController {
    @FXML
    private Button addTestCaseButton;
    @FXML
    private TextField input, output;

    private AddExerciseItemController controller;

    @FXML
    public void initialize(){
        addTestCaseButton.setDisable(true);
        input.setOnKeyReleased(this::onInputChange);
        output.setOnKeyReleased(this::onInputChange);
        addTestCaseButton.setOnAction(event -> {
            TestCase testCase = new TestCase();
            testCase.setInput(input.getText());
            testCase.setOutput(output.getText());
            controller.addTestCase(testCase);
        });
    }

    private void onInputChange(KeyEvent event){
        if(input.getText().length() > 0 && input.getText().length() > 0){
            addTestCaseButton.setDisable(false);
        }
    }

    public AddExerciseItemController getController() {
        return controller;
    }

    public void setController(AddExerciseItemController controller) {
        this.controller = controller;
    }

}
