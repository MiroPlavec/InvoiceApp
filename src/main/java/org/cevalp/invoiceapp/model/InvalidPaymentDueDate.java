package org.cevalp.invoiceapp.model;

public class InvalidPaymentDueDate extends RuntimeException {
    public InvalidPaymentDueDate(String message) {
        super(message);
    }
}
