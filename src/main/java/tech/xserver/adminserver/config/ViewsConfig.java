package tech.xserver.adminserver.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import tech.xserver.adminserver.DTO.ViewsDto;
import tech.xserver.adminserver.entity.ViewsEntity;

@Configuration
public class ViewsConfig {
    private final KafkaTemplate<String, ViewsEntity> kafkaTemplate;

    public ViewsConfig(KafkaTemplate<String, ViewsEntity> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Bean
    @Qualifier("viewsEntityClass")
    public Class<ViewsEntity> entityClass() {
        return ViewsEntity.class;
    }

    public void sendMessage(ViewsEntity view) {
        kafkaTemplate.send("viewsTopic", view);
    }
}
