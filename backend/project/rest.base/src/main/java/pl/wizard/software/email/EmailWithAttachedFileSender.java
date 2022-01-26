package pl.wizard.software.email;

import pl.wizard.software.dto.ShoppingListDto;

import java.io.FileOutputStream;

public abstract class EmailWithAttachedFileSender {

    public void send(ShoppingListDto shoppingList) {
        FileData fileData = parse(shoppingList);
        FileOutputStream file = saveToFile(fileData);
        sendEmailWithAttachedFile(file);
    }

    FileData parse(ShoppingListDto shoppingList) {
        return null;
    }

    public abstract FileOutputStream saveToFile(FileData fileData);

    void sendEmailWithAttachedFile(FileOutputStream file) {

    }
}
