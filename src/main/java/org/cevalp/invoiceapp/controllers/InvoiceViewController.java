package org.cevalp.invoiceapp.controllers;

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


    public TextField senderCompanyName;
    public TextField senderIco;
    public TextField senderDic;
    public TextField senderCity;
    public TextField senderStreet;
    public TextField senderHouseNumber;
    public TextField senderPsc;
    public TextField senderBank;
    public TextField senderSwift;
    public TextField senderIban;

    public TextField receiverCompanyName;
    public TextField receiverIco;
    public TextField receiverDic;
    public TextField receiverCity;
    public TextField receiverStreet;
    public TextField receiverHouseNumber;
    public TextField receiverPsc;


    public TextField variableSymbol;
    public TextField constantSymbol;
    public TextField amount;
    public DatePicker creationDate;
    public DatePicker payDueDate;
    public TextField invoiceId;
    public TextField description;

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

}
