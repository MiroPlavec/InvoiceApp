module org.cevalp.invoiceapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.apache.pdfbox;
    requires java.datatransfer;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires org.tukaani.xz;
    requires java.desktop;
    requires com.google.gson;

    opens org.cevalp.invoiceapp to javafx.fxml;
    opens org.cevalp.invoiceapp.controllers to javafx.fxml;
    opens org.cevalp.invoiceapp.model to com.google.gson;

    exports org.cevalp.invoiceapp;
    exports org.cevalp.invoiceapp.controllers;
}