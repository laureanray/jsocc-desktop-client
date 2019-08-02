package com.fozf.jsocc;

import com.fozf.jsocc.controllers.LoginController;
import com.fozf.jsocc.utils.App;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle(App.name);
        primaryStage.setScene(new Scene(root, App.SM_WIDTH, App.SM_HEIGHT));
        LoginController controller = loader.getController();
        primaryStage.setOnCloseRequest(e -> controller.close());
        primaryStage.getIcons().add(App.icon);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
