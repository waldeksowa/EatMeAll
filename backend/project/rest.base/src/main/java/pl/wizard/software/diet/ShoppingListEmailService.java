package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wizard.software.dto.ShoppingListDto;
import pl.wizard.software.email.ShoppingListAsExcelSender;
import pl.wizard.software.email.ShoppingListAsPdfSender;

@Service
@RequiredArgsConstructor
public class ShoppingListEmailService {

    public void sendEmailWithPdf(ShoppingListDto shoppingList, String recipient) {
        ShoppingListAsPdfSender emailPdf = new ShoppingListAsPdfSender();
        emailPdf.send(shoppingList, recipient);
    }

    public void sendEmailWithExcel(ShoppingListDto shoppingList, String recipient) {
        ShoppingListAsExcelSender emailExcel = new ShoppingListAsExcelSender();
        emailExcel.send(shoppingList, recipient);
    }
}
