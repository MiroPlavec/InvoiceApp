package org.cevalp.invoiceapp.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class ViewSwitcher {

    private static Map<View, Parent> storedViews = new HashMap<>();
    private static Scene scene;

    public static void setScene(Scene scene) {
        ViewSwitcher.scene = scene;
    }

    public static void switchScene(View view){
        Parent root;
        if(storedViews.containsKey(view)){
            root = storedViews.get(view);
        }else{
            URL viewUrl = ViewSwitcher.class.getResource(view.getViewPath());
            if (viewUrl == null) throw new FXMLNotFoundException(view);
            try {
                root = FXMLLoader.load(viewUrl);
                storedViews.put(view, root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        scene.setRoot(root);
    }
}
