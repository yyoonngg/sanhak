package com.project.sanhak.util.mail;

import com.project.sanhak.main.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;

@Slf4j
@Service
public class EmailService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String sender, String recipient, String contents) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String subject = sender + "님이 보낸 메세지 입니다.";
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(contents, false);
            javaMailSender.send(mimeMessage);
            log.info("Email sent successfully");
        } catch (MessagingException e) {
            log.error("Email sending failed", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}