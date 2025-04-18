package org.cevalp.invoiceapp.navigation;

public enum View {
    MAIN("main-view.fxml"),
    INVOICE("invoice-view.fxml"),
    SENDER("addSender-view.fxml"),
    RECIPIENT("addRecipient-view.fxml");

    private final String viewName;
    private final String folder = "/views/";

    View(String viewName){
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public String getViewPath() {
        return folder + viewName;
    }
}
