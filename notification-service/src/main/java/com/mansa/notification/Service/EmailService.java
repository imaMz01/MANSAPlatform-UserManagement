package com.mansa.notification.Service;

import com.mansa.notification.Dtos.EmailRequest;
import com.mansa.notification.Dtos.EmailVerificationRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.WriteBuffer;
import org.springframework.context.annotation.Bean;
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

//    @Bean
//    public Consumer<EmailRequest> consumeNotification() {
//        return message -> {
//            try {
//                System.out.println("Received notification :" + message.toString());
//                sendNotification(message);
//                System.out.println("Email sent successfully to " + message.getEmail());
//            } catch (Exception e) {
//                System.err.println("Failed to process email notification: " + e.getMessage());
//            }
//
//        };
//    }
//
//    @Bean
//    public Consumer<EmailVerificationRequest> consumeVerificationEmail() {
//        return message -> {
//            try {
//                System.out.println("Received notification :" + message.toString());
//                sendVerificationEmail(message);
//                System.out.println("Email sent successfully to " + message.getEmail());
//            } catch (Exception e) {
//                System.err.println("Failed to process email verification: " + e.getMessage());
//            }
//
//        };
//    }

    @Bean
    public Consumer<EmailVerificationRequest> consumeVerificationAdminInvitation() {
        return message -> {
            try {
                System.out.println("Received notification :" + message.toString());
                sendAdminInvitation(message);
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
        sendEmail(emailRequest.getEmail(), context,"Subscription request "+emailRequest.getStatus(),"SubscriptionNotification");
    }

    public void sendVerificationEmail(EmailVerificationRequest request) throws MessagingException {

        Context context = new Context();
        context.setVariable("token", request.getToken());
        context.setVariable("lastname", request.getLastName());
        sendEmail(request.getEmail(), context,"Email verification","VerificationEmail");
    }

    public void sendAdminInvitation(EmailVerificationRequest emailRequest) throws MessagingException {

        Context context = new Context();
        context.setVariable("token", emailRequest.getLastName());
        sendEmail(emailRequest.getEmail(), context,"Admin Invitation","AdminInvitationEmail");
    }

    private void sendEmail(String to, Context context,String subject, String template) throws MessagingException {
        String content = templateEngine.process(template, context);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper email = new MimeMessageHelper(message,true);
        email.setTo(to);
        email.setSubject(subject);
        email.setText(content,true);
        mailSender.send(message);
    }
}
