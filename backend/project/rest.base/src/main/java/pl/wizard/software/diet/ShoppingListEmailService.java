package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import pl.wizard.software.dto.ShoppingListDto;
import pl.wizard.software.email.EmailSenderService;
import pl.wizard.software.email.ShoppingListAsExcelSender;
import pl.wizard.software.email.ShoppingListAsPdfSender;

@Service
@RequiredArgsConstructor
public class ShoppingListEmailService {

    private final EmailSenderService emailSenderService;
    @Autowired
    private ApplicationContext context;

    public void sendEmailWithPdf(ShoppingListDto shoppingList, String recipient) {
        ShoppingListAsPdfSender emailPdf = context.getBean(ShoppingListAsPdfSender.class, emailSenderService);
//        ShoppingListAsPdfSender emailPdf = new ShoppingListAsPdfSender(emailSenderService);
        emailPdf.send(shoppingList, recipient);
    }

    public void sendEmailWithExcel(ShoppingListDto shoppingList, String recipient) {
        ShoppingListAsExcelSender emailExcel = context.getBean(ShoppingListAsExcelSender.class, emailSenderService);
//        ShoppingListAsExcelSender emailExcel = new ShoppingListAsExcelSender(emailSenderService);
        emailExcel.send(shoppingList, recipient);
    }
}
