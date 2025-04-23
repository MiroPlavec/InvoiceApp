package org.cevalp.invoiceapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.cevalp.invoiceapp.model.InvoiceDetails;
import org.cevalp.invoiceapp.model.PaymentDetails;
import org.cevalp.invoiceapp.model.Recipient;
import org.cevalp.invoiceapp.model.Sender;
import org.cevalp.invoiceapp.navigation.View;
import org.cevalp.invoiceapp.navigation.ViewSwitcher;
import org.cevalp.invoiceapp.utils.PDFMaker;
import org.cevalp.invoiceapp.utils.QREncoder;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class InvoiceViewController {


    @FXML
    private TextField senderCompanyName;
    @FXML
    private TextField senderIco;
    @FXML
    private TextField senderDic;
    @FXML
    private TextField senderCity;
    @FXML
    private TextField senderStreet;
    @FXML
    private TextField senderHouseNumber;
    @FXML
    private TextField senderPsc;
    @FXML
    private TextField senderBank;
    @FXML
    private TextField senderSwift;
    @FXML
    private TextField senderIban;

    @FXML
    private TextField receiverCompanyName;
    @FXML
    private TextField receiverIco;
    @FXML
    private TextField receiverDic;
    @FXML
    private TextField receiverCity;
    @FXML
    private TextField receiverStreet;
    @FXML
    private TextField receiverHouseNumber;
    @FXML
    private TextField receiverPsc;


    @FXML
    private TextField variableSymbol;
    @FXML
    private TextField constantSymbol;
    @FXML
    private TextField amount;
    @FXML
    private DatePicker creationDate;
    @FXML
    private DatePicker payDueDate;
    @FXML
    private TextField invoiceId;
    @FXML
    private TextField description;
    @FXML
    private ComboBox<String> paymentWay;



    public void createInvoice(){
        Sender sender = createSender();

        Recipient recipient = createRecipient();
        InvoiceDetails invoiceDetails = createInvoiceDetails();

        PaymentDetails paymentDetails = createPaymentDetails();

        QREncoder encoder = new QREncoder();
        String encodedData = encoder.encode(paymentDetails);
        byte[] qr = encoder.createQrCode(encodedData);

        try (PDFMaker pdfMaker = new PDFMaker(sender, recipient, invoiceDetails, qr)){
            pdfMaker.makePDF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Sender createSender(){
        return new Sender.SenderBuilder()
                .company(senderCompanyName.getText())
                .ico(senderIco.getText())
                .dic(senderDic.getText())
                .city(senderCity.getText())
                .street(senderStreet.getText())
                .houseNumber(senderHouseNumber.getText())
                .postcode(senderPsc.getText())
                .bank(senderBank.getText())
                .swift(senderSwift.getText())
                .iban(senderIban.getText())
                .build();
    }

    private Recipient createRecipient(){
        return new Recipient.RecipientBuilder()
                .companyName(receiverCompanyName.getText())
                .ico(receiverIco.getText())
                .dic(receiverDic.getText())
                .city(receiverCity.getText())
                .street(receiverStreet.getText())
                .houseNumber(receiverHouseNumber.getText())
                .postCode(receiverPsc.getText())
                .build();
    }

    private InvoiceDetails createInvoiceDetails(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return new InvoiceDetails.InvoiceDetailsBuilder()
                .variableSymbol(variableSymbol.getText())
                .constantSymbol(constantSymbol.getText())
                .amount(amount.getText())
                .creationDate(creationDate.getValue().format(formatter))
                .payDueDate(payDueDate.getValue().format(formatter))
                .invoiceId(invoiceId.getText())
                .description(description.getText())
                .paymentWay(paymentWay.getValue())
                .build();
    }

    private PaymentDetails createPaymentDetails(){
        return new PaymentDetails.PaymentDetailsBuilder()
                .iban(senderIban.getText())
                .amount(amount.getText())
                .variableSymbol(variableSymbol.getText())
                .constantSymbol(constantSymbol.getText())
                .swift(senderSwift.getText())
                .build();
    }


    public void back(){
        ViewSwitcher.switchScene(View.MAIN);
    }

    public void showSenderPicker(){
        ViewSwitcher.showPicker(this, Sender.class);
    }

    public void showRecipientPicker(){
        ViewSwitcher.showPicker(this, Recipient.class);
    }

    public void setSenderData(Sender sender){
        senderCompanyName.setText(sender.getCompanyName());
        senderIco.setText(sender.getIco());
        senderDic.setText(sender.getDic());
        senderCity.setText(sender.getCity());
        senderStreet.setText(sender.getStreet());
        senderHouseNumber.setText(sender.getHouseNumber());
        senderPsc.setText(sender.getPostcode());
        senderBank.setText(sender.getBank());
        senderSwift.setText(sender.getSwift());
        senderIban.setText(sender.getIban());
    }

    public void setReceiverData(Recipient recipient){
        receiverCompanyName.setText(recipient.getCompanyName());
        receiverIco.setText(recipient.getIco());
        receiverDic.setText(recipient.getDic());
        receiverCity.setText(recipient.getCity());
        receiverStreet.setText(recipient.getStreet());
        receiverHouseNumber.setText(recipient.getHouseNumber());
        receiverPsc.setText(recipient.getPostcode());
    }

}
