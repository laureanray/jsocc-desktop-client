package com.fozf.jsocc;

import com.fozf.jsocc.controllers.LoginController;
import com.fozf.jsocc.utils.App;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle(App.name);
        primaryStage.setScene(new Scene(root, App.SM_WIDTH, App.SM_HEIGHT));
        LoginController controller = loader.getController();
        primaryStage.setOnCloseRequest(e -> controller.close());
        primaryStage.getIcons().add(App.icon);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);
        this.stage = primaryStage;
        primaryStage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
