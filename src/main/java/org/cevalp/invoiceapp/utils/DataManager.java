package org.cevalp.invoiceapp.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.cevalp.invoiceapp.model.AbstractUser;
import org.cevalp.invoiceapp.model.Recipient;
import org.cevalp.invoiceapp.model.Sender;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// 1. save user with bankDetails (load)

// 2. save recipient (load)

public class DataManager {
    private final Path appDir = Paths.get(System.getProperty("user.home"), "InvoiceApp");
    private final String senderFile = "senders.json";
    private final String recipientFile = "recipients.json";

    private final Gson gson = new Gson();

    private void checkDirectory() throws DataManagerException {
        try {
            Files.createDirectories(appDir);
        } catch (IOException e) {
            throw new DataManagerException("Can not create directory %s".formatted(appDir.toString()));
        }
    }


    public void save(AbstractUser user) throws DataManagerException{
        checkDirectory();
        List<AbstractUser> users;
        Path filePath;
        if(user instanceof Sender){
            filePath = appDir.resolve(senderFile);
            users = load(Sender.class);
        }else {
            filePath = appDir.resolve(recipientFile);
            users = load(Recipient.class);
        }
        users.add(user);
        try (Writer writer = new FileWriter(filePath.toString())){
            gson.toJson(users, writer);
        } catch (IOException e) {
            throw new DataManagerException("Sorry can not save data");
        }
    }


    public List<AbstractUser> load(Class<? extends AbstractUser> clazz) throws DataManagerException{
        Path filePath;
        if (clazz == Sender.class){
            filePath = appDir.resolve(senderFile);
        }else {
            filePath = appDir.resolve(recipientFile);
        }
        if(Files.notExists(filePath)) return new ArrayList<>();
        try (Reader reader = new FileReader(filePath.toString())){
            Type userType =TypeToken.getParameterized(List.class, clazz).getType();
            List<AbstractUser> users = gson.fromJson(reader, userType);
            return (users != null) ? users : new ArrayList<>();
        } catch (IOException e) {
            throw new DataManagerException("Sorry can not load data from file");
        }
    }


}
