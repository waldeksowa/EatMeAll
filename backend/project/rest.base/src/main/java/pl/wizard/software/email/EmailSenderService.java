package pl.wizard.software.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    public static final String EMAIL_FROM = "waldek@szkola-dobrego-kodu.pl";
    private final JavaMailSender javaMailSender;

    public void send(String emailTo, String subject, String messageBody, File attachment) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(EMAIL_FROM);
            messageHelper.setTo(emailTo);
            messageHelper.setSubject(subject);
            messageHelper.setText(messageBody);
            if (attachment != null) {
                messageHelper.addAttachment(attachment.getName(), attachment);
            }
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
