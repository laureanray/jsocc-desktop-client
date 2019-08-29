package com.fozf.jsocc.utils;
import com.fozf.jsocc.models.Instructor;
import com.fozf.jsocc.models.Student;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class App {
    public static String name = "Java Simulator and Online Code Checker";
    public static String abbrev = "JSOCC";
    public static int WINDOW_HEIGHT = 540;
    public static int WINDOW_WIDTH = 1200;
    public static int SM_WIDTH = 400;
    public static int SM_HEIGHT = 500;
    public static int INS_WIDTH = 1000;
    public static int INS_HEIGHT = 490;
    public static int ALERT_WIDTH = 400;
    public static int ALERT_HEIGHT = 200;
    public static int ALERT_WIDTH_SM = 400;
    public static int ALERT_HEIGHT_SM = 175;
    public static Image icon = new Image(App.class.getResourceAsStream("/images/icon.png"));
    public static Image statusGreen = new Image(App.class.getResourceAsStream("/images/green.png"));
    public static Image statusRed = new Image(App.class.getResourceAsStream("/images/red.png"));
    public static Student student;
    public static Instructor instructor;
    public static boolean isStudent = true;



}
