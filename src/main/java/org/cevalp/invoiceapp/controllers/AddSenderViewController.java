package org.cevalp.invoiceapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.cevalp.invoiceapp.model.Sender;
import org.cevalp.invoiceapp.navigation.View;
import org.cevalp.invoiceapp.navigation.ViewSwitcher;

public class AddSenderViewController {

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

    @FXML
    private TextField bank;

    @FXML
    private TextField swift;

    @FXML
    private TextField iban;

    private Sender sender;


    public void back(){
        ViewSwitcher.switchScene(View.MAIN);
    }

    public void save(){
    }
}
