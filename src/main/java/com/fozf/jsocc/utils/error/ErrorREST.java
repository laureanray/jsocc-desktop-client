package com.fozf.jsocc.utils.error;

import javafx.scene.control.Alert;

public class ErrorREST {
    private Alert alert;
    public ErrorREST(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("ErrorREST");
        alert.setContentText(contentText);
    }

    public Alert getAlert() {
        return alert;
    }
}
