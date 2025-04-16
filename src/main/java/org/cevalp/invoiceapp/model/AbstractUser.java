package org.cevalp.invoiceapp.model;

public abstract class AbstractUser {
    private String companyName;
    private String city;
    private String street;
    private String houseNumber;
    private String postcode;
    private String ICO;
    private String DIC;

    public AbstractUser(String companyName, String city, String street, String houseNumber, String postcode, String ICO, String DIC) {
        this.companyName = companyName;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.ICO = ICO;
        this.DIC = DIC;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCity() {
        return city;
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

    public String getStreet() {
        return street;
    }
}
