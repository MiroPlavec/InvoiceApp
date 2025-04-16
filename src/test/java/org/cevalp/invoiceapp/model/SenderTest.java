package org.cevalp.invoiceapp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SenderTest {


    @Test
    public void postcodeTest(){
        String postcode = "05904";
        assertDoesNotThrow(()->{
            new Sender.SenderBuilder().postcode(postcode).build();
        });
    }

    @Test
    public void icoTest(){
        String ico = "00";
        assertDoesNotThrow(() -> {
            new Sender.SenderBuilder().ico(ico);
        });
    }

    @Test
    public void ibanTest(){
        Sender sender = new Sender.SenderBuilder().iban("SK1909000000005229767659").build();
        System.out.println(sender.getAccountNumber());
        System.out.println(sender.getBankNumber());
    }

    @Test
    public void dateTest(){
        LocalDate today = LocalDate.now();
        System.out.println(today);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.out.println(today.format(formatter));
    }


}