package org.cevalp.invoiceapp.model;

public class InvoiceDetails {
    private String variableSymbol;
    private String constantSymbol;
    private double amount;
    private String creationDate;
    private String payDueDate;
    private String description;
    private String invoiceId;

    private InvoiceDetails(InvoiceDetailsBuilder builder){
        this.variableSymbol = builder.variableSymbol;
        this.constantSymbol = builder.constantSymbol;
        this.amount = builder.amount;
        this.creationDate = builder.creationDate;
        this.payDueDate = builder.payDueDate;
        this.description = builder.description;
        this.invoiceId = builder.invoiceId;
    }

    public String getVariableSymbol() {
        return variableSymbol;
    }

    public String getConstantSymbol() {
        return constantSymbol;
    }

    public double getAmount() {
        return amount;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getPayDueDate() {
        return payDueDate;
    }

    public String getDescription() {
        return description;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public static class InvoiceDetailsBuilder{
        private String variableSymbol;
        private String constantSymbol;
        private double amount;
        private String creationDate;
        private String payDueDate;
        private String description;
        private String invoiceId;

        public InvoiceDetails build(){
            return new InvoiceDetails(this);
        }

        public InvoiceDetailsBuilder variableSymbol(String variableSymbol){
            this.variableSymbol = variableSymbol;
            return this;
        }

        public InvoiceDetailsBuilder constantSymbol(String constantSymbol){
            this.constantSymbol = constantSymbol;
            return this;
        }

        public InvoiceDetailsBuilder amount(String amount){
            this.amount = Double.parseDouble(amount);
            return this;
        }

        public InvoiceDetailsBuilder creationDate(String creationDate){
            this.creationDate = creationDate;
            return this;
        }

        public InvoiceDetailsBuilder payDueDate(String payDueDate){
            this.payDueDate = payDueDate;
            return this;
        }

        public InvoiceDetailsBuilder description(String description){
            this.description = description;
            return this;
        }

        public InvoiceDetailsBuilder invoiceId(String invoiceId){
            this.invoiceId = invoiceId;
            return this;
        }
    }
}
