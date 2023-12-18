package spring.project.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.io.*;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String body) throws MessagingException, IOException {
        StringBuilder csvString = new StringBuilder();
        for (String line : body.split("\n")) {
            csvString.append(line);
            csvString.append("\n");
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(csvString.toString());
        messageHelper.addAttachment("report.csv", new ByteArrayDataSource(csvString.toString().getBytes(), "text/csv"));

        mailSender.send(message);
    }
    public void sendPassword(String userEmail) throws IOException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        mimeMessageHelper.setTo(userEmail);
        mimeMessageHelper.setSubject("Create new password");
        mimeMessageHelper.setText("Link for create new password http://localhost:8080/newPassword");

        mailSender.send(message);
    }
}
