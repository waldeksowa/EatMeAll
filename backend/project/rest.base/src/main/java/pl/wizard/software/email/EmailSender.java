package pl.wizard.software.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

class EmailSender {

    public static final String SMTP_HOST = "smtp.dpoczta.pl";
    public static final String SMTP_PROTOCOL = "smtp";
    public static final String SMTP_PORT = "25";
    public static final String SMTP_AUTH = "true";
    public static final String SMTP_USER_NAME = "waldek@szkola-dobrego-kodu.pl";
    public static final String SMTP_PASSWORD = "SzkolaWaldek11";
    public static final String EMAIL_SUBJECT = "This is your shopping list";
    public static final String MESSAGE_TYPE = "text/html; charset=utf-8";

    public void send(String recipient, String messageBody, File file) {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.transport.protocol", SMTP_PROTOCOL);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", SMTP_AUTH);

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER_NAME, SMTP_PASSWORD);
            }
        };

        Session session = Session.getInstance(props, authenticator);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USER_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(EMAIL_SUBJECT);
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(messageBody, MESSAGE_TYPE);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message );
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
