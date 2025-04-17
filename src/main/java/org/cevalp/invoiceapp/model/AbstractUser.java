package org.cevalp.invoiceapp.model;

public abstract class AbstractUser {
    private String companyName;
    private String city;
    private String street;
    private String houseNumber;
    private String postcode;
    private String ico;
    private String dic;

    public AbstractUser(String companyName, String city, String street, String houseNumber, String postcode, String ICO, String DIC) {
        this.companyName = companyName;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.ico = ICO;
        this.dic = DIC;
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

    public String getIco() {
        return ico;
    }

    public String getDic() {
        return dic;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return "AbstractUser{" +
                "companyName='" + companyName + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", postcode='" + postcode + '\'' +
                ", ico='" + ico + '\'' +
                ", dic='" + dic + '\'' +
                '}';
    }
}
