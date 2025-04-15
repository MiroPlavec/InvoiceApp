package org.cevalp.invoiceapp.controllers;

import org.cevalp.invoiceapp.navigation.View;
import org.cevalp.invoiceapp.navigation.ViewSwitcher;

public class InvoiceViewController {

    public void back(){
        ViewSwitcher.switchScene(View.MAIN);
    }
}
