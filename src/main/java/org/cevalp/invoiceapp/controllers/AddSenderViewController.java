package org.cevalp.invoiceapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.cevalp.invoiceapp.model.InvalidIbanException;
import org.cevalp.invoiceapp.model.Sender;
import org.cevalp.invoiceapp.navigation.View;
import org.cevalp.invoiceapp.navigation.ViewSwitcher;
import org.cevalp.invoiceapp.utils.DataManager;
import org.cevalp.invoiceapp.utils.DataManagerException;

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

    @FXML
    private Pane root;

    private final DataManager dataManager;
    private String ibanValue;
    private boolean ibanListener;

    public AddSenderViewController(){
        dataManager = new DataManager();
    }

    public void back(){
        ViewSwitcher.switchScene(View.MAIN);
    }


    public void save(){
        try{
        Sender sender = new Sender.SenderBuilder()
                .company(companyName.getText())
                .ico(ico.getText())
                .dic(dic.getText())
                .city(city.getText())
                .postcode(psc.getText())
                .street(street.getText())
                .houseNumber(houseNumber.getText())
                .bank(bank.getText())
                .swift(swift.getText())
                .iban(iban.getText())
                .build();

            dataManager.save(sender);
        } catch (InvalidIbanException e){
            invalidIban();
        }catch (DataManagerException e) {
            throw new RuntimeException(e);
        }

    }


    private void invalidIban(){
        iban.setStyle("-fx-background-color: #ff6666;");
        if(!ibanListener){
            iban.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!oldValue.equals(newValue)) iban.setStyle("-fx-background-color: white;");
            });
        }
    }
}
