package org.cevalp.invoiceapp.navigation;

public enum View {
    MAIN("main-view.fxml", true),
    INVOICE("invoice-view.fxml", false);

    private final String viewName;
    private final boolean isCacheable;
    private final String folder = "/views/";

    View(String viewName, boolean isCacheable){
        this.viewName = viewName;
        this.isCacheable = isCacheable;
    }

    public String getViewName() {
        return viewName;
    }

    public String getViewPath() {
        return folder + viewName;
    }
}
