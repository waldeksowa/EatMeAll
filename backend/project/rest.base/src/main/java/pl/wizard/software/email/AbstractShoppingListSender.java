package pl.wizard.software.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import pl.wizard.software.dto.ShoppingListDto;
import pl.wizard.software.mapper.ShoppingListFileDataMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@RequiredArgsConstructor
public abstract class AbstractShoppingListSender {

    public static final String MESSAGE_BODY_PREFIX = "Shopping list from ";
    private ShoppingListFileData fileData;
    @Autowired
    private EmailSenderService emailSenderService;

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

    protected ShoppingListFileData parse(ShoppingListDto shoppingList) {
        return ShoppingListFileDataMapper.mapToFileData(shoppingList);
    }

    public abstract File saveToFile(ShoppingListFileData fileData) throws FileNotFoundException;

    protected void sendEmailWithAttachedFile(String emailTo, File file) {
        LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(fileData.getShoppingListDate()));
        String subject = MESSAGE_BODY_PREFIX + localDate.toString();
        String messageBody = subject;
        emailSenderService.send(emailTo, subject, messageBody, file);
    }
}
