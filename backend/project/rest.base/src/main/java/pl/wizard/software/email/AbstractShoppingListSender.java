package pl.wizard.software.email;

import pl.wizard.software.dto.ShoppingListDto;
import pl.wizard.software.mapper.ShoppingListFileDataMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;


public abstract class AbstractShoppingListSender {

    public static final String MESSAGE_BODY_PREFIX = "Shopping list from ";
    private ShoppingListFileData fileData;
    private final EmailSenderService emailSenderService;

    public AbstractShoppingListSender(EmailSenderService emailSenderService) {
        this(new ShoppingListFileData(), emailSenderService);
    }

    public AbstractShoppingListSender(ShoppingListFileData fileData, EmailSenderService emailSenderService) {
        this.fileData = fileData;
        this.emailSenderService = emailSenderService;
    }

    public void send(ShoppingListDto shoppingList, String emailTo) {
        fileData = parse(shoppingList);
        File file = null;
        try {
            file = saveToFile(fileData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sendEmailWithAttachedFile(emailTo, file);
    }

    private ShoppingListFileData parse(ShoppingListDto shoppingList) {
        return ShoppingListFileDataMapper.mapToFileData(shoppingList);
    }

    protected abstract File saveToFile(ShoppingListFileData fileData) throws FileNotFoundException;

    private void sendEmailWithAttachedFile(String emailTo, File file) {
        LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(fileData.getShoppingListDate()));
        String subject = MESSAGE_BODY_PREFIX + localDate.toString();
        String messageBody = subject;
        emailSenderService.send(emailTo, subject, messageBody, file);
    }
}
