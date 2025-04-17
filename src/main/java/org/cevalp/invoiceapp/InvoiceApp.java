package org.cevalp.invoiceapp;

import javafx.application.Application;
import javafx.stage.Stage;

public class InvoiceApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String userHome = System.getProperty("user.home");
        System.out.println(System.getProperty("os.name"));
        System.out.println(userHome);
    }

    //    @Override
//    public void start(Stage stage) throws Exception {
//        stage.setResizable(false);
//
//        URL mainViewUrl = InvoiceApp.class.getResource(View.MAIN.getViewPath());
//        if (mainViewUrl == null) throw new FXMLNotFoundException(View.MAIN);
//        Parent root = FXMLLoader.load(mainViewUrl);
//
//        Scene mainScene = new Scene(root, 1000, 700);
//        ViewSwitcher.setScene(mainScene);
//        stage.setScene(mainScene);
//        stage.show();
//    }

}
