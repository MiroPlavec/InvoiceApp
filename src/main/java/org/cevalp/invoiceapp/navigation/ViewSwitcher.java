package org.cevalp.invoiceapp.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public abstract class ViewSwitcher {

    private static Stage mainWindow;
    private static Scene scene;

    public static void setScene(Stage stage) {
        ViewSwitcher.scene = stage.getScene();
        ViewSwitcher.mainWindow = stage;
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

    public static void popupWindow(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
