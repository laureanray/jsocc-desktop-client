package com.fozf.jsocc.utils;

import com.fozf.jsocc.controllers.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewBootstrapper {
    public enum Size {
        LARGE,
        INSIDE,
        SMALL,
        CUSTOM_ALERT,
        CUSTOM_ALERT_SM,
        LOGIN
    };

    public enum Type {
        DIALOG,
        PARTIAL,
        NORMAL
    }

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene;

    public ViewBootstrapper(String filename, Size size, Type type) throws IOException {

        if(type.equals(Type.NORMAL)){
            this.loader = new FXMLLoader(App.class.getResource("/fxml/"+filename+".fxml"));
        } else if (type.equals(Type.PARTIAL)) {
            this.loader = new FXMLLoader(App.class.getResource("/fxml/partial/"+filename+".fxml"));
        } else{
            this.loader = new FXMLLoader(App.class.getResource("/fxml/dialog/"+filename+".fxml"));
        }

        if(size.equals(Size.LARGE)){
            this.scene = new Scene(this.loader.load(), App.WINDOW_WIDTH, App.WINDOW_HEIGHT);
        }else if(size.equals(Size.SMALL)){
            this.scene = new Scene(this.loader.load(), App.SM_WIDTH, App.SM_HEIGHT);
        }else if(size.equals(size.CUSTOM_ALERT)){
            this.scene = new Scene(this.loader.load(), App.ALERT_WIDTH, App.ALERT_HEIGHT);
        }else if(size.equals(size.CUSTOM_ALERT_SM)){
            this.scene = new Scene(this.loader.load(), App.ALERT_WIDTH_SM, App.ALERT_HEIGHT_SM);
        }else if(size.equals(size.LOGIN)){
            this.scene = new Scene(this.loader.load(), 300, 400);
        }else {
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
