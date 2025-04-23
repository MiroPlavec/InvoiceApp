package org.cevalp.invoiceapp.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.cevalp.invoiceapp.controllers.InvoiceViewController;
import org.cevalp.invoiceapp.controllers.PickerController;
import org.cevalp.invoiceapp.model.AbstractUser;
import org.cevalp.invoiceapp.model.Recipient;
import org.cevalp.invoiceapp.model.Sender;

import java.io.IOException;
import java.net.URL;

public abstract class ViewSwitcher {

    private static Stage mainWindow;
    private static Stage pickerWindow;
    private static Scene scene;

    public static void setStage(Stage stage) {
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

    public static void infoAlert(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static void errorAlert(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static void showPicker(InvoiceViewController invoiceViewController, Class<? extends AbstractUser> clazz){
        pickerWindow = new Stage();
        pickerWindow.initOwner(mainWindow);
        pickerWindow.initModality(Modality.WINDOW_MODAL);
        pickerWindow.setResizable(false);

        URL fxmlFileUrl = ViewSwitcher.class.getResource(View.PICKER.getViewPath());
        if (fxmlFileUrl == null) throw new FXMLNotFoundException(View.PICKER);
        FXMLLoader loader = new FXMLLoader(fxmlFileUrl);
        Parent root;
        try {
            root = loader.load();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        PickerController controller = loader.getController();
        controller.setInvoiceViewController(invoiceViewController);
        controller.populate(clazz);

        if(clazz == Sender.class) pickerWindow.setTitle("Dodávatelia");
        if(clazz == Recipient.class) pickerWindow.setTitle("Príjemcovia");

        pickerWindow.setScene(new Scene(root, 600, 300));
        pickerWindow.show();
    }


    public static void closePicker(){
        if (pickerWindow != null){
            pickerWindow.close();
            pickerWindow = null;
        }
    }
}
