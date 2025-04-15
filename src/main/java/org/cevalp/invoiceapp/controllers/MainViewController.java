package org.cevalp.invoiceapp.controllers;

import org.cevalp.invoiceapp.navigation.View;
import org.cevalp.invoiceapp.navigation.ViewSwitcher;

public class MainViewController {


    public void createInvoice(){
        ViewSwitcher.switchScene(View.INVOICE);
    }

    public void addSender(){
        ViewSwitcher.switchScene(View.SENDER);
    }

}
