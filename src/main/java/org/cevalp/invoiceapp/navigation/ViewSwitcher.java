package org.cevalp.invoiceapp.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public abstract class ViewSwitcher {

    private static Stage stage;

    public static void setStage(Stage stage) {
        ViewSwitcher.stage = stage;
    }

    public static void switchScene(View view){
        URL viewPath = ViewSwitcher.class.getResource(view.getViewPath());
        if(viewPath == null) throw new FXMLNotFoundException(view);
        try {
            Parent root = FXMLLoader.load(viewPath);
            stage.setScene(new Scene(root, 300, 300));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
