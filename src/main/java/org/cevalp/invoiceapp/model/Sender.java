package org.cevalp.invoiceapp.model;

// represent man who sends a invoice, who have done a work
public class Sender extends AbstractUser{

    private String bank = "Slovenska sporitelna, a.s.";
    private String iban = "SK1909000000005229767659";
    private String swift = "GIBASKBX";
    private String accountNumber = "5229767659";
    private String bankNumber = "0900";
    private String variableSymbol = "2";
    private String constantSymbol = "";

    public Sender(String companyName, String address, String postcode, String ICO, String DIC) {
        super(companyName, address, postcode, ICO, DIC);
    }

    public String getVariableSymbol() {
        return variableSymbol;
    }

    public String getConstantSymbol() {
        return constantSymbol;
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
}
