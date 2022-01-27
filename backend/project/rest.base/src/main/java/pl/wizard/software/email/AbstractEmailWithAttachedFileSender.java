package pl.wizard.software.email;

import pl.wizard.software.dto.ShoppingListDto;
import pl.wizard.software.mapper.ShoppingListFileDataMapper;

import java.io.File;

public abstract class AbstractEmailWithAttachedFileSender {

    public static final String MESSAGE_PREFIX = "Shopping list from ";
    private ShoppingListFileData fileData;

    public void send(ShoppingListDto shoppingList, String recipient) {
        fileData = parse(shoppingList);
        File file = saveToFile(fileData);
        sendEmailWithAttachedFile(recipient, file);
    }

    protected ShoppingListFileData parse(ShoppingListDto shoppingList) {
        return ShoppingListFileDataMapper.mapToFileData(shoppingList);
    }

    public abstract File saveToFile(ShoppingListFileData fileData);

    protected void sendEmailWithAttachedFile(String recipient, File file) {
        String messageBody = MESSAGE_PREFIX + fileData.getShoppingListDate().toString();
        EmailSender emailSender = new EmailSender();
        emailSender.send(recipient, messageBody, file);
    }
}
