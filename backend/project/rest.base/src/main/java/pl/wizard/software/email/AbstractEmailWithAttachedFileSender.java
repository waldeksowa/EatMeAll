package pl.wizard.software.email;

import pl.wizard.software.dto.ShoppingListDto;
import pl.wizard.software.mapper.ShoppingListFileDataMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public abstract class AbstractEmailWithAttachedFileSender {

    public static final String MESSAGE_BODY_PREFIX = "Shopping list from ";
    private ShoppingListFileData fileData;

    public void send(ShoppingListDto shoppingList, String recipient) {
        fileData = parse(shoppingList);
        File file = null;
        try {
            file = saveToFile(fileData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sendEmailWithAttachedFile(recipient, file);
    }

    protected ShoppingListFileData parse(ShoppingListDto shoppingList) {
        return ShoppingListFileDataMapper.mapToFileData(shoppingList);
    }

    public abstract File saveToFile(ShoppingListFileData fileData) throws FileNotFoundException;

    protected void sendEmailWithAttachedFile(String recipient, File file) {
        LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(fileData.getShoppingListDate()));
        String messageBody = MESSAGE_BODY_PREFIX + localDate.toString();
        EmailSender emailSender = new EmailSender();
        emailSender.send(recipient, messageBody, file);
    }
}
