package org.cevalp.invoiceapp.model;

// represent man who sends a invoice, who have done a work
public class Sender extends AbstractUser{

    private String bank;
    private String iban;
    private String swift;
    private String accountNumber;
    private String bankNumber;


    private Sender(SenderBuilder senderBuilder) {
        super(senderBuilder.companyName,
                senderBuilder.city,
                senderBuilder.street,
                senderBuilder.postcode,
                senderBuilder.ico,
                senderBuilder.dic);
        this.bank = senderBuilder.bank;
        this.iban = senderBuilder.iban;
        this.swift = senderBuilder.swift;
        this.accountNumber = senderBuilder.accountNumber;
        this.bankNumber = senderBuilder.bankNumber;
    }

    public String getBank() {
        return bank;
    }

    public String getIban() {
        return iban;
    }

    public String getSwift() {
        return swift;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }


    public static class SenderBuilder{
        private String companyName;
        private String city;
        private String street;
        private String postcode;
        private String ico;
        private String dic;
        private String bank;
        private String iban;
        private String swift;
        private String bankNumber;
        private String accountNumber;

        public Sender build(){
            return new Sender(this);
        }

        public SenderBuilder company(String companyName){
            this.companyName = companyName;
            return this;
        }

        public SenderBuilder city(String city){
            this.city = city;
            return this;
        }

        public SenderBuilder street(String street){
            this.street = street;
            return this;
        }

        public SenderBuilder postcode(String postcode){
            if(postcode.matches("[0-9]+")) throw new RuntimeException("PSC must contain only numerical values. -> IMPLEMENTS LATER");
            this.postcode = postcode;
            return this;
        }

        public SenderBuilder ico(String ico){
            if(ico.matches("[0-9]+")) throw new RuntimeException("ICO must contain only numerical values. -> IMPLEMENTS LATER");
            this.ico = ico;
            return this;
        }

        public SenderBuilder dic(String dic){
            if(dic.matches("[0-9]+")) throw new RuntimeException("DIC must contain only numerical values. -> IMPLEMENTS LATER");
            this.dic = dic;
            return this;
        }

        public SenderBuilder bank(String bank){
            this.bank = bank;
            return this;
        }

        public SenderBuilder iban(String iban){
            if(!checkIban(iban)) throw new RuntimeException("Invalid IBAN -> IMPLEMENTS LATER");
            getNumbersFromIban(iban);
            this.iban = iban;
            return this;
        }

        private boolean checkIban(String iban){
            if(iban.length() != 24) return false;
            if(!iban.substring(0, 2).equals("SK")) return false;
            return true;
        }

        private void getNumbersFromIban(String iban){
            this.bankNumber = iban.substring(4, 8);
            this.accountNumber = iban.substring(iban.length() - 10);
        }

        public SenderBuilder swift(String swift){
            this.swift = swift;
            return this;
        }
    }
}
