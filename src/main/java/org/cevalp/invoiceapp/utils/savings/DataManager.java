package org.cevalp.invoiceapp.utils.savings;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.cevalp.invoiceapp.model.AbstractUser;
import org.cevalp.invoiceapp.model.IDManager;
import org.cevalp.invoiceapp.model.Recipient;
import org.cevalp.invoiceapp.model.Sender;
import org.cevalp.invoiceapp.utils.DataManagerException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final Path appDir = Paths.get(System.getProperty("user.home"), "InvoiceApp");
    private final Path senderFilePath;
    private final Path recipientFilePath;

    private static DataManager instance;
    private List<Sender> senders;
    private List<Recipient> recipients;
    private final Gson gson;

    private DataManager(){
        senders = new ArrayList<>();
        recipients = new ArrayList<>();
        gson = new Gson();
        senderFilePath = appDir.resolve("senders.json");
        recipientFilePath = appDir.resolve("recipients.json");
    }

    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    public List<Sender> getSenders(){
        return senders;
    }

    public List<Recipient> getRecipients(){
        return recipients;
    }

    public void addSender(Sender sender){
        senders.add(sender);
    }

    public void addRecipient(Recipient recipient){
        recipients.add(recipient);
    }

    public void loadAppData(){
        loadSpecificUser(senders, Sender.class, senderFilePath);
        loadSpecificUser(recipients, Recipient.class, recipientFilePath);
        IDManager.findSenderId(senders);
        IDManager.findRecipientId(recipients);
    }

    public void saveAppData(){
        saveSpecificUser(senders, senderFilePath);
        saveSpecificUser(recipients, recipientFilePath);
    }

    public static void checkDirectory() throws DataManagerException {
        try {
            Files.createDirectories(appDir);
        } catch (IOException e) {
            throw new DataManagerException("Can not create directory %s".formatted(appDir.toString()));
        }
    }

    private <T extends AbstractUser> void loadSpecificUser(List<T> users, Class<T> clazz, Path path){
        try(FileReader reader = new FileReader(path.toString())){
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            List<T> loadedUsers = gson.fromJson(reader, listType);
            if(loadedUsers != null) users.addAll(loadedUsers);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File %s was not found".formatted(path.toString()));
        } catch (IOException e) {
            throw new RuntimeException("Problem while trying to read from %s".formatted(path.toString()));
        }
    }

    private <T extends AbstractUser> void saveSpecificUser(List<T> users, Path path){
        try(FileWriter writer = new FileWriter(path.toString())){
            gson.toJson(users, writer);
        } catch (IOException e) {
            throw new RuntimeException("Problem while trying to save to %s".formatted(path.toString()));
        }
    }


}
