package tech.xserver.adminserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.xserver.adminserver.DTO.MailDto;
import tech.xserver.adminserver.config.MailConfig;
import tech.xserver.adminserver.mappers.MailMapper;
import tech.xserver.adminserver.model.Mail;

@RestController
@Slf4j
public class MailController {
//    private final KafkaTemplate<String, Mail> kafkaTemplate;
    private final MailConfig mailConfig;
    private final MailMapper mailMapper;

    public MailController(KafkaTemplate<String, Mail> kafkaTemplate, MailMapper mailMapper, MailConfig mailConfig) {
//        this.kafkaTemplate = kafkaTemplate;
        this.mailMapper = mailMapper;
        this.mailConfig = mailConfig;
    }

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody MailDto mail) {
        Mail mailx = mailMapper.mapFrom(mail);
        log.warn("Sending mail to: " + mailx);
//        kafkaTemplate.send("mailTopic", mailx);
        mailConfig.sendMessage(mailx);
        return "Mail sent successfully";
    }
}
