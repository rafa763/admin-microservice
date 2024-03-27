package tech.xserver.adminserver.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic mailTopic() {
        return TopicBuilder.name("mailTopic").build();
    }
    @Bean
    public NewTopic viewsTopic() {
        return TopicBuilder.name("viewsTopic").build();
    }
}
