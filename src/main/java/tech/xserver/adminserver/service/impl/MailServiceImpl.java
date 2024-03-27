package tech.xserver.adminserver.service.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tech.xserver.adminserver.model.Mail;
import tech.xserver.adminserver.service.EmailService;

@Service
public class MailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage message;

    public MailServiceImpl(JavaMailSender javaMailSender, SimpleMailMessage message) {
        this.javaMailSender = javaMailSender;
        this.message = message;
    }
    @Override
    @KafkaListener(topics = "mailTopic", groupId = "gid", containerFactory = "factory")
    public void sendEmail(Mail emailMessage) {
        message.setTo(emailMessage.getTo());
        message.setSubject(emailMessage.getSubject());
        message.setText(emailMessage.getText());
        javaMailSender.send(message);
    }
}
