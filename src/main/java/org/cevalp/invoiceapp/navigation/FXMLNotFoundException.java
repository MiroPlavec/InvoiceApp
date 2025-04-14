package org.cevalp.invoiceapp.navigation;

public class FXMLNotFoundException extends RuntimeException {
    public FXMLNotFoundException(String message) {
        super(message);
    }

    public FXMLNotFoundException(View view){
        super("View \"" + view.getViewName() + "\" can not be found");
    }
}
