package org.cevalp.invoiceapp.model;

import java.text.Normalizer;
import java.util.Locale;

public class PaymentDetails {

    private final String invoiceId = "";
    private final int paymentsCount = 1;
    private final int paymentOptions = 1; // values 1 2 4
    private double amount;
    private String currencyCode = "EUR";
    private String paymentDueDate = "";
    private String variableSymbol;
    private String constantSymbol;
    private String specificSymbol = "";
    private final String originatorsReferenceInformation = ""; // when 3 symbols are filled, this doesnt have to be
    private String note = "";
    private final int accountCount = 1;
    private String iban;
    private String swift;

    // these fields are not necessary for simple payment
//    private final int standingOrderExt = 0; // when paymentOptions = 2
//    private final int directDebitExt = 0; // when paymentOptions = 4
//    private String beneficiaryName = "";
//    private String beneficiaryAddress1 = "";
//    private String beneficiaryAddress2 = "";



    private PaymentDetails(PaymentDetailsBuilder builder){
        this.iban = builder.iban;
        this.amount = builder.amount;
        this.variableSymbol = builder.variableSymbol;
        this.constantSymbol = builder.constantSymbol;
        this.swift = builder.swift;
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
             iban,
             swift
//             String.valueOf(standingOrderExt),
//             String.valueOf(directDebitExt),
//             beneficiaryName,
//             beneficiaryAddress1,
//             beneficiaryAddress2
             );

    }


    public static class PaymentDetailsBuilder{

        private String iban;
        private double amount;
        private String variableSymbol;
        private String constantSymbol;
        private String swift;


        public PaymentDetails build(){
            PaymentDetails paymentDetails = new PaymentDetails(this);
            return paymentDetails;
        }

        // remove diacritic from string
        private void normalize(String data){
            Normalizer.normalize(data, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
        }

        public PaymentDetailsBuilder iban(String iban){
            this.iban = iban;
            return this;
        }

        public PaymentDetailsBuilder amount(String amount){
            this.amount = Double.parseDouble(amount);
            return this;
        }

        public PaymentDetailsBuilder variableSymbol(String variableSymbol){
            this.variableSymbol = variableSymbol;
            return this;
        }

        public PaymentDetailsBuilder constantSymbol(String constantSymbol){
            this.constantSymbol = constantSymbol;
            return this;
        }

        public PaymentDetailsBuilder swift(String swift){
            this.swift = swift;
            return this;
        }



    }

}
