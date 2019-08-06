package com.fozf.jsocc.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewBootstrap {
    public enum Size {
        LARGE,
        SMALL
    };

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene;

    public ViewBootstrap(String filename, Size size) throws IOException {
        this.loader = new FXMLLoader(App.class.getResource("/fxml/"+filename+".fxml"));
        if(size.equals(Size.LARGE)){
            this.scene = new Scene(this.loader.load(), App.WINDOW_WIDTH, App.WINDOW_HEIGHT);
        }else{
            this.scene = new Scene(this.loader.load(), App.SM_WIDTH, App.SM_HEIGHT);
        }

        this.stage = new Stage();
        this.stage.setFullScreen(false);
        this.stage.setResizable(false);
        this.stage.setTitle(filename);
        this.stage.setScene(scene);
        this.stage.getIcons().add(App.icon);
    }

    public Stage getStage() {
        return stage;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public Scene getScene() {
        return scene;
    }
}
