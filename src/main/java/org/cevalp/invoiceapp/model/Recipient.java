package org.cevalp.invoiceapp.model;

// represent someone who receive invoice, who pays for service
public class Recipient extends AbstractUser{

    public Recipient(String companyName, String city, String street, String postcode, String ICO, String DIC) {
        super(companyName, city, street, postcode, ICO, DIC);
    }
}
