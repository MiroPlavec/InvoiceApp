package org.cevalp.invoiceapp.model;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PaymentDetails {

    private final String invoiceId = "";
    private final int paymentsCount = 1;
    private final int paymentOptions = 1; // values 1 2 4
    private double amount;
    private String currencyCode;
    private String paymentDueDate = "";
    private String variableSymbol;
    private String constantSymbol = "";
    private String specificSymbol = "";
    private final String originatorsReferenceInformation = ""; // when 3 symbols are filled, this doesnt have to be
    private String note = "";
    private final int accountCount = 1;
    private String IBAN;
    private String swift = "TATRSKBX";

    // these fields are not necessary for simple payment
    private final int standingOrderExt = 0; // when paymentOptions = 2
    private final int directDebitExt = 0; // when paymentOptions = 4
    private String beneficiaryName = "";
    private String beneficiaryAddress1 = "";
    private String beneficiaryAddress2 = "";



    private PaymentDetails(PaymentDetailsBuilder builder){
        this.IBAN = builder.IBAN;
        this.amount = builder.amount;
        this.currencyCode = builder.currencyCode;
        this.variableSymbol = builder.variableSymbol;
    }


    public String getDetail(){
     return String.join("\t",
             invoiceId,
             String.valueOf(paymentsCount),
             String.valueOf(paymentOptions),
             String.format(Locale.US, "%.2f", amount),
             currencyCode,
             String.valueOf(paymentDueDate),
             variableSymbol,
             constantSymbol,
             specificSymbol,
             originatorsReferenceInformation,
             note,
             String.valueOf(accountCount),
             IBAN,
             swift,
             String.valueOf(standingOrderExt),
             String.valueOf(directDebitExt),
             beneficiaryName,
             beneficiaryAddress1,
             beneficiaryAddress2
             );

    }


    public static class PaymentDetailsBuilder{

        private final String IBAN;
        private final double amount;
        private final String currencyCode;
        private String variableSymbol;
        private String paymentDueDate;

        public PaymentDetailsBuilder(String IBAN, double amount, String currencyCode) {
            this.IBAN = IBAN;
            this.amount = amount;
            this.currencyCode = currencyCode;
        }

        // remove diacritic from string
        private void normalize(String data){
            Normalizer.normalize(data, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
        }

        public PaymentDetailsBuilder variableSymbol(String variableSymbol){
            this.variableSymbol = variableSymbol;
            return this;
        }

        public PaymentDetailsBuilder date(LocalDate date){
            if(date == null) date = LocalDate.now();
            if(date.isBefore(LocalDate.now())) throw new InvalidPaymentDueDate("Due date can not be before today");
            paymentDueDate = DateTimeFormatter.ofPattern("yyyyMMdd").format(date);

            return this;
        }


        public PaymentDetails build(){
            PaymentDetails paymentDetails = new PaymentDetails(this);
            return paymentDetails;
        }


    }

}
