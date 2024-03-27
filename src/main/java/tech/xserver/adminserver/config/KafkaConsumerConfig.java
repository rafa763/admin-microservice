package tech.xserver.adminserver.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import tech.xserver.adminserver.DTO.ViewsDto;
import tech.xserver.adminserver.entity.ViewsEntity;
import tech.xserver.adminserver.model.Mail;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public <T> ConsumerFactory<String, T> consumerFactory(@Qualifier("mailClass") Class<T> mailClass, @Qualifier("viewsEntityClass") Class<T> entityClass) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        // is this the cleanest way to do this? idk, but it works
        JsonDeserializer<T> valueDeserializer = new JsonDeserializer<>(mailClass != null ? mailClass : entityClass);
        valueDeserializer.addTrustedPackages("tech.xserver.adminserver.entity");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), valueDeserializer);
    }

    @Bean
    public <T> KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, T>> factory(ConsumerFactory<String, T> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Mail>> mailKafkaListenerContainerFactory(@Qualifier("mailClass") Class<Mail> mailClass) {
        return factory(consumerFactory(mailClass, null));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ViewsEntity>> viewsKafkaListenerContainerFactory(@Qualifier("viewsEntityClass") Class<ViewsEntity> entityClass) {
        return factory(consumerFactory(null, entityClass));
    }
}
