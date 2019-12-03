package com.fozf.jsocc.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class ExerciseController {
    @FXML
    public WebView webView;
    @FXML
    public Button uploadCodeBtn;
    @FXML
    public Button runCodeBtn;
    @FXML
    public Text filenameText;
    @FXML
    public TextArea sourceCodeTextArea;
    @FXML
    public TextArea inputTextArea;
    @FXML
    public TextArea outputTextArea;
    @FXML
    public Text statusMessageText;
    @FXML
    public ProgressIndicator progressIndicator;

    private Stage stage;
    private String filePath = "temp\\Solution.java";
    private String fileName = "Solution";
    private String javacPath = "jdk\\bin\\javac.exe";
    private String javaPath = "jdk\\bin\\java.exe";
    private String classPath = "temp\\";
    private String sourceCode;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        progressIndicator.managedProperty().bind(progressIndicator.visibleProperty());
        progressIndicator.setVisible(false);
        FileChooser fileChooser = new FileChooser();
        WebEngine we = webView.getEngine();
        we.loadContent("<b> Hello World! <b>");
//        we.load("http://google.com");
        System.out.println("Initialize Exercise");

        uploadCodeBtn.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(stage);
            filenameText.setText(file.getName());
            loadFileToTextArea(file);
        });

        runCodeBtn.setOnAction(e -> {
            progressIndicator.setVisible(true);
            progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            compile();
            run();
        });

        sourceCodeTextArea.setOnKeyReleased(e -> {
            System.out.println("Key released.");
            sourceCode = sourceCodeTextArea.getText();
            writeCodeToFile("Solution.java", sourceCode);
        });
    }

    private void loadFileToTextArea(File file) {
        try {
            sourceCode = FileUtils.readFileToString(file, "UTF-8");
            sourceCodeTextArea.setText(sourceCode);
            writeCodeToFile("Solution.java", sourceCode);
        } catch(IOException e){
            System.out.println("Error loading file. ");
        }
    }

    private void compile() {
        String command = String.format("\"%s\" \"%s\"", javacPath, filePath);
//        String[] command = {
//                String.format("\"%s\" \"%s\" && \"%s\" -cp \"%s\" \"%s\"", javacPath, filePath, javaPath, classPath, fileName),
//        };
        String s = null;
        String stdout = "";
        String stderr = "";
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            while ((s = stdInput.readLine()) != null) {
//                System.out.println(s);
                    stdout += ("\n" + s);
            }

            while ((s = stdError.readLine()) != null) {
                stderr += ("\n" + s);
            }

            if(stderr.length() > 0){
                statusMessageText.setText("Compilation Error");
            }else {
                statusMessageText.setText("Compilation Successful");
                progressIndicator.setVisible(false);
            }

        }catch(IOException e){
        }
    }

    private void run() {
        String[] command = {
                String.format("\"%s\" -cp \"%s\" \"%s\"", javaPath, classPath, fileName),
        };
        String s = null;
        String stdout = "";
        String stderr = "";
        try {
            Process p = Runtime.getRuntime().exec(command);
            System.out.println(command[0]);
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

            outputTextArea.setText(stdout);

        }catch(IOException e){

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
}
