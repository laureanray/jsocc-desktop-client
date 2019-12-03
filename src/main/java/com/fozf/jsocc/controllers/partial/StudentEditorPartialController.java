package com.fozf.jsocc.controllers.partial;
import com.fozf.jsocc.models.TestCase;
import org.apache.commons.io.FilenameUtils;
import com.fozf.jsocc.models.ExerciseItem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;

public class StudentEditorPartialController {

    @FXML
    public Text exerciseItemTitle, exerciseItemDescription, filename, statusMessageText;

    @FXML
    public Button uploadCodeButton, submitCodeButton, runCodeButton;

    @FXML
    public TextArea codeTextArea;

    ArrayList<Boolean> testCasesResult = new ArrayList<>();

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Stage stage;

    private String filePath = "";
    private String fileName = "";
    private String javacPath = "jdk\\bin\\javac.exe";
    private String javaPath = "jdk\\bin\\java.exe";
    private String classPath = "temp";
    private String sourceCode;

    public ExerciseItem getExerciseItem() {
        return exerciseItem;
    }

    public void setExerciseItem(ExerciseItem exerciseItem) {
        this.exerciseItem = exerciseItem;

        exerciseItemTitle.setText(exerciseItem.getItemTitle());
        exerciseItemDescription.setText(exerciseItem.getItemDescription());
    }

    private ExerciseItem exerciseItem;

    public void initialize(){
        System.out.println("Initialize");

        FileChooser fileChooser = new FileChooser();


        uploadCodeButton.setOnAction(ev -> {
            File file = fileChooser.showOpenDialog(stage);
            if(file != null){
                filename.setText(file.getName());
                String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());


                loadFileToTextArea(file);
                runCodeButton.setDisable(false);
            }
        });

        runCodeButton.setOnAction(ev -> {
            compile();
            check();
            for(Boolean bool : testCasesResult){
                System.out.println(bool);
            }
        });


        runCodeButton.setDisable(true);
        submitCodeButton.setDisable(true);


    }

    private void loadFileToTextArea(File file){
        try {
            sourceCode = FileUtils.readFileToString(file, "UTF-8");
            codeTextArea.setText(sourceCode);
            // Set fileName
            fileName = FilenameUtils.removeExtension(file.getName());
            filePath = "temp\\" + file.getName();
            writeCodeToFile(file.getName(), sourceCode);

            // Save every keystrokes
            codeTextArea.setOnKeyReleased(ev -> {
                System.out.println("Writing");
                writeCodeToFile(file.getName(), codeTextArea.getText());
            });
        } catch(IOException e){
            System.out.println("Error loading file. ");
        }
    }

    private void writeCodeToFile(String fileName, String content){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("temp/"+fileName));
            writer.write(content);
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private String run(String input) {
        String command =
                String.format("\"%s\" -cp \"%s\" \"%s\"", javaPath, classPath, fileName);
        String s = null;
        String stdout = "";
        String stderr = "";
        try {
            Process p = Runtime.getRuntime().exec(command);
            System.out.println(command);

            if(input.length() != 0){
                OutputStream os = p.getOutputStream();
                os.write(input.getBytes());
                os.flush();
            }

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            while ((s = stdInput.readLine()) != null) {
                stdout += ("\n" + s);
            }

            while ((s = stdError.readLine()) != null) {
                stderr += ("\n" + s);
            }

            System.out.println(stdout);
        }catch(IOException e){

        }

        return stdout;
    }

    private void compile() {
        String command = String.format("\"%s\" \"%s\"", javacPath, filePath);

        String s = null;
        String stdout = "";
        String stderr = "";

        OutputStream procIn = null;
        InputStream procOut = null;

        try {
            Process p = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));





            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                stdout += ("\n" + s);
            }

            while ((s = stdError.readLine()) != null) {
                stderr += ("\n" + s);
            }

            System.out.println(stdout);
            System.out.println(stdout.length());



            if(stderr.length() > 0){
                statusMessageText.setText("Compilation Error");
            }else {
                statusMessageText.setText("Compilation Successful");
            }

        }catch(IOException e){
        }
    }

    private void check(){
        for(TestCase testCase : exerciseItem.getTestCases()){
            System.out.println(testCase.getInput());
            String res = run(testCase.getInput());

            System.out.println("[" + res + "] : [" + testCase.getOutput() + "]");

            if(res.trim().equals(testCase.getOutput().trim())){
                testCasesResult.add(true);
            }else{
                testCasesResult.add(false);
            }
        }

    }
}

