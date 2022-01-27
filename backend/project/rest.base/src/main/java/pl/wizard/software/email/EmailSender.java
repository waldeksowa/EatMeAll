package pl.wizard.software.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {

    public static final String SMTP_HOST = "smtp.dpoczta.pl";
    public static final String SMTP_PROTOCOL = "smtp";
    public static final String SMTP_PORT = "25";
    public static final String SMTP_AUTH = "true";
    public static final String SMTP_USER_NAME = "waldek@szkola-dobrego-kodu.pl";
    public static final String SMTP_PASSWORD = "SzkolaWaldek11";
    public static final String EMAIL_SUBJECT = "This is your shopping list";
    public static final String MESSAGE_TYPE = "text/html; charset=utf-8";

    public void send(String recipient, String messageBody, File file) {
        Properties props = setProperties();
        Session session = getSession(props);
        try {
            Message message = createMessage(recipient, messageBody, file, session);
            Transport.send(message );
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    private Properties setProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.transport.protocol", SMTP_PROTOCOL);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", SMTP_AUTH);
        return props;
    }

    private Session getSession(Properties props) {
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER_NAME, SMTP_PASSWORD);
            }
        };
        Session session = Session.getInstance(props, authenticator);
        return session;
    }

    private Message createMessage(String recipient, String messageBody, File file, Session session) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_USER_NAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(EMAIL_SUBJECT);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(messageBody, MESSAGE_TYPE);
//        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
//        attachmentBodyPart.attachFile(file);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
//        multipart.addBodyPart(attachmentBodyPart);
        message.setContent(multipart);
        return message;
    }
}
