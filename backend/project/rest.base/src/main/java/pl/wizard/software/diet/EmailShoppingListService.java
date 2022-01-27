package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wizard.software.email.EmailSender;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailShoppingListService {

    public void sendEmail() {
        EmailSender emailSender = new EmailSender();
        emailSender.send("waldek@szkola-dobrego-kodu.pl", "test mail", null);
    }

}
