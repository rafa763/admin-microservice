package tech.xserver.adminserver.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import tech.xserver.adminserver.model.Mail;

@Configuration
public class MailConfig {
    private final KafkaTemplate<String, Mail> kafkaTemplate;

    @Bean
    @Qualifier("mailClass")
    public Class<Mail> mailClass() {
        return Mail.class;
    }
    public MailConfig(KafkaTemplate<String, Mail> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Bean
    public SimpleMailMessage simpleMailMessage() {
        return new SimpleMailMessage();
    }

    public void sendMessage(Mail mail) {
        kafkaTemplate.send("mailTopic", mail);
    }
}
