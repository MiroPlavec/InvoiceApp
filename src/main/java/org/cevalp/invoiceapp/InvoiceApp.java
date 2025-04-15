package org.cevalp.invoiceapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.cevalp.invoiceapp.navigation.FXMLNotFoundException;
import org.cevalp.invoiceapp.navigation.View;
import org.cevalp.invoiceapp.navigation.ViewSwitcher;

import java.net.URL;

public class InvoiceApp extends Application {

    private static final int STAGE_WIDTH = 1000;
    private static final int STAGE_HEIGHT = 700;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);

        URL mainViewUrl = InvoiceApp.class.getResource(View.MAIN.getViewPath());
        if (mainViewUrl == null) throw new FXMLNotFoundException(View.MAIN);
        Parent root = FXMLLoader.load(mainViewUrl);

        Scene mainScene = new Scene(root, 1000, 700);
        ViewSwitcher.setScene(mainScene);
        stage.setScene(mainScene);
        stage.show();
    }

}
