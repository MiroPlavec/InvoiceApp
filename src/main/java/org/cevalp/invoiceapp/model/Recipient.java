package org.cevalp.invoiceapp.model;

// represent someone who receive invoice, who pays for service
public class Recipient extends AbstractUser{

    private Recipient(RecipientBuilder builder) {
        super(
                builder.companyName,
                builder.city,
                builder.street,
                builder.houseNumber,
                builder.postcode,
                builder.ico,
                builder.dic
        );
    }

    public void update(Recipient recipient){
        super.update(recipient);
    }




    public static class RecipientBuilder{
        private String companyName;
        private String city;
        private String street;
        private String houseNumber;
        private String postcode;
        private String ico;
        private String dic;

        public Recipient build(){
            return new Recipient(this);
        }

        public RecipientBuilder companyName(String companyName){
            this.companyName = companyName;
            return this;
        }

        public RecipientBuilder city(String city){
            this.city = city;
            return this;
        }

        public RecipientBuilder street(String street){
            this.street = street;
            return this;
        }

        public RecipientBuilder houseNumber(String houseNumber){
            this.houseNumber = houseNumber;
            return this;
        }

        public RecipientBuilder postCode(String postcode){
            this.postcode = postcode;
            return this;
        }

        public RecipientBuilder ico(String ico){
            this.ico = ico;
            return this;
        }

        public RecipientBuilder dic(String dic){
            this.dic = dic;
            return this;
        }
    }
}
