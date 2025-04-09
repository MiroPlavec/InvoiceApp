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

    opens org.cevalp.invoiceapp to javafx.fxml;
    exports org.cevalp.invoiceapp;
}