package com.mansa.notification.Service;

import com.mansa.notification.Dtos.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Bean
    public Consumer<EmailRequest> consumeNotification() {
        return message -> {
            try {
                System.out.println("Received notification :" + message.toString());
                sendNotification(message);
                System.out.println("Email sent successfully to " + message.getEmail());
            } catch (Exception e) {
                System.err.println("Failed to process email notification: " + e.getMessage());
            }

        };
    }

    public void sendNotification(EmailRequest emailRequest) throws MessagingException {

        Context context = new Context();
        context.setVariable("status", emailRequest.getStatus());
        context.setVariable("lastname", emailRequest.getLastName());
        String content = templateEngine.process("SubscriptionNotification", context);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper email = new MimeMessageHelper(message,true);
        email.setTo(emailRequest.getEmail());
        email.setSubject("Subscription request "+emailRequest.getStatus());
        email.setText(content,true);

        mailSender.send(message);
    }
}
