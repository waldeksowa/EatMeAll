package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.dto.ShoppingListDto;
import pl.wizard.software.email.EmailWithAttachedPdfSender;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailShoppingListService {

    public void sendEmailWithPdf(ShoppingListDto shoppingList, String recipient) {
        EmailWithAttachedPdfSender emailPdf = new EmailWithAttachedPdfSender();
        emailPdf.send(shoppingList, recipient);
    }
}
