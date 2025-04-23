package org.cevalp.invoiceapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.cevalp.invoiceapp.model.Recipient;
import org.cevalp.invoiceapp.navigation.View;
import org.cevalp.invoiceapp.navigation.ViewSwitcher;
import org.cevalp.invoiceapp.utils.DataManager;
import org.cevalp.invoiceapp.utils.DataManagerException;

public class AddRecipientViewController {

    @FXML
    private TextField companyName;

    @FXML
    private TextField ico;

    @FXML
    private TextField dic;

    @FXML
    private TextField city;

    @FXML
    private TextField psc;

    @FXML
    private TextField street;

    @FXML
    private TextField houseNumber;

    public void back(){
        ViewSwitcher.switchScene(View.MAIN);
    }

    public void save(){
        Recipient recipient = new Recipient.RecipientBuilder()
                .companyName(companyName.getText())
                .ico(ico.getText())
                .dic(dic.getText())
                .city(city.getText())
                .postCode(psc.getText())
                .street(street.getText())
                .houseNumber(houseNumber.getText())
                .build();

        try {
            DataManager.save(recipient);
            ViewSwitcher.infoAlert("Príjemnca bol úspešne vytvorený");
            back();
        }catch (DataManagerException e){
            ViewSwitcher.errorAlert("Nemôžem uložiť príjemncu, je potrebné ho zadať ručne pri vytváraní faktúry");
        }
    }
}
