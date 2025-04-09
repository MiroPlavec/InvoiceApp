module org.cevalp.invoiceapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.cevalp.invoiceapp to javafx.fxml;
    exports org.cevalp.invoiceapp;
}