package org.cevalp.invoiceapp.model;

import java.util.List;

public abstract class IDManager {

    static int senderID;
    static int recipientID;


    public static void findSenderId(List<Sender> senders){
        senderID = senders.stream().mapToInt(AbstractUser::getId).max().orElse(0) + 1;
    }

    public static void findRecipientId(List<Recipient> recipients){
        recipientID = recipients.stream().mapToInt(AbstractUser::getId).max().orElse(0) + 1;
    }
}
