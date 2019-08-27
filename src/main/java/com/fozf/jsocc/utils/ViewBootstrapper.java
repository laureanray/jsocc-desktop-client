package com.fozf.jsocc.utils;

import com.fozf.jsocc.controllers.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ViewBootstrapper {
    public enum Size {
        LARGE,
        INSIDE,
        SMALL
    };

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene;

    public ViewBootstrapper(String filename, Size size) throws IOException {

        if(size.equals(Size.LARGE)){
            this.loader = new FXMLLoader(App.class.getResource("/fxml/"+filename+".fxml"));
            this.scene = new Scene(this.loader.load(), App.WINDOW_WIDTH, App.WINDOW_HEIGHT);
        }else if(size.equals(Size.SMALL)){
            this.loader = new FXMLLoader(App.class.getResource("/fxml/"+filename+".fxml"));
            this.scene = new Scene(this.loader.load(), App.SM_WIDTH, App.SM_HEIGHT);
        }else {
            this.loader = new FXMLLoader(App.class.getResource("/fxml/partial/"+filename+".fxml"));
            this.scene = new Scene(this.loader.load(), App.INS_WIDTH, App.INS_HEIGHT);
        }

        this.stage = new Stage();
        this.stage.setFullScreen(false);
        this.stage.setResizable(false);
        this.stage.setTitle(filename);
        this.stage.setScene(scene);
        this.stage.getIcons().add(App.icon);

        if(filename.equals("Login")){
            LoginController controller = this.loader.getController();
            this.stage.setOnCloseRequest(e -> controller.close());
        }
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
