package org.cevalp.invoiceapp.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

public abstract class ViewSwitcher {

    private static Scene scene;

    public static void setScene(Scene scene) {
        ViewSwitcher.scene = scene;
    }

    public static void switchScene(View view){
        Parent root;
        URL viewUrl = ViewSwitcher.class.getResource(view.getViewPath());
        if (viewUrl == null) throw new FXMLNotFoundException(view);
        try {
            root = FXMLLoader.load(viewUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene.setRoot(root);
    }
}
