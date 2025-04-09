package org.cevalp.invoiceapp.model;

public abstract class AbstractUser {
    private String companyName;
    private String address;
    private String postcode;
    private String ICO;
    private String DIC;

    public AbstractUser(String companyName, String address, String postcode, String ICO, String DIC) {
        this.companyName = companyName;
        this.address = address;
        this.postcode = postcode;
        this.ICO = ICO;
        this.DIC = DIC;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getICO() {
        return ICO;
    }

    public String getDIC() {
        return DIC;
    }
}
