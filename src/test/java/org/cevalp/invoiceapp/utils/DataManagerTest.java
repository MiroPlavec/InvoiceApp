package org.cevalp.invoiceapp.utils;

import org.cevalp.invoiceapp.model.AbstractUser;
import org.cevalp.invoiceapp.model.Recipient;
import org.cevalp.invoiceapp.model.Sender;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataManagerTest {


    @Test
    void saveSender() throws DataManagerException {
        Sender sender = new Sender
                .SenderBuilder()
                .company("Miroslav Plavec")
                .city("Spisske Hanusovce")
                .street("")
                .houseNumber("141")
                .postcode("05904")
                .ico("456").dic("654")
                .bank("Slovenska sporitelna")
                .iban("Sk090000015")
                .swift("TARTAs")
                .build();

        DataManager.save(sender);
    }

    @Test
    void saveRecipient() throws DataManagerException{
        Recipient recipient = new Recipient.RecipientBuilder()
                .companyName("Spolocnost")
                .city("Kosice")
                .houseNumber("")
                .street("Nova")
                .dic("999")
                .ico("888")
                .postCode("0145")
                .build();

        DataManager.save(recipient);
    }


    @Test
    void loadSender() throws DataManagerException{
        List<AbstractUser> users = DataManager.load(Sender.class);
        users.forEach(System.out::println);
    }

    @Test
    void loadRecipient() throws DataManagerException{
        List<AbstractUser> users = DataManager.load(Recipient.class);
        users.forEach(System.out::println);
    }
}