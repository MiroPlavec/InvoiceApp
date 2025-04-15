package org.cevalp.invoiceapp.model;

import java.util.Date;

public class InvoiceDetails {
    private String variableSymbol;
    private String constantSymbol;
    private double amount;
    private Date creationDate;
    private Date payDueDate;

    public String getVariableSymbol() {
        return variableSymbol;
    }

    public String getConstantSymbol() {
        return constantSymbol;
    }

    public double getAmount() {
        return amount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getPayDueDate() {
        return payDueDate;
    }
}
