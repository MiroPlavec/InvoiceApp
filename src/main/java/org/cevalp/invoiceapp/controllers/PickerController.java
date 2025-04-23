package org.cevalp.invoiceapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.cevalp.invoiceapp.model.AbstractUser;
import org.cevalp.invoiceapp.model.Recipient;
import org.cevalp.invoiceapp.model.Sender;
import org.cevalp.invoiceapp.navigation.ViewSwitcher;
import org.cevalp.invoiceapp.utils.DataManager;
import org.cevalp.invoiceapp.utils.DataManagerException;

import java.util.List;

public class PickerController {

    @FXML
    private ListView<String> userListView;

    private List<AbstractUser> users;
    private InvoiceViewController invoiceViewController;

    public void closePicker(){
        ViewSwitcher.closePicker();
    }

    public void setInvoiceViewController(InvoiceViewController invoiceViewController){
        this.invoiceViewController = invoiceViewController;
    }

    public void getSenderInfo(){
        int idx = userListView.getSelectionModel().getSelectedIndices().get(0);
        AbstractUser pickedUser = users.get(idx);
        if(pickedUser instanceof Sender sender) invoiceViewController.setSenderData(sender);
        if(pickedUser instanceof Recipient recipient) invoiceViewController.setReceiverData(recipient);
        closePicker();
    }

    public void populate(Class<? extends AbstractUser> clazz){
        try {
            if(clazz == Sender.class) users = DataManager.load(Sender.class);
            else if (clazz == Recipient.class) users = DataManager.load(Recipient.class);
            else throw new IllegalArgumentException("%s not supported for this method".formatted(clazz.getName()));
        } catch (DataManagerException e) {
            ViewSwitcher.errorAlert("Nie je možné nahrať dodávateľov, zadaj hodnoty ručne");
            return;
        }

        List<String> data = users.stream()
                .map(e -> "Firma: %s, ICO: %s, DIC: %s".formatted(e.getCompanyName(), e.getIco(), e.getDic()))
                .toList();

        userListView.getItems().addAll(data);
    }
}
