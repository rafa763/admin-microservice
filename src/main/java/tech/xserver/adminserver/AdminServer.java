package tech.xserver.adminserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import tech.xserver.adminserver.entity.Role;
import tech.xserver.adminserver.entity.RoleEntity;
import tech.xserver.adminserver.entity.UserEntity;
import tech.xserver.adminserver.repo.RoleRepo;
import tech.xserver.adminserver.repo.UserRepo;
import tech.xserver.adminserver.service.RoleService;
import tech.xserver.adminserver.service.UserService;
//import tech.xserver.adminserver.config.RsaKeyProperties;

@SpringBootApplication
@EnableFeignClients
//@EnableConfigurationProperties(RsaKeyProperties.class)
public class AdminServer {
    public static void main(String[] args) {
        SpringApplication.run(AdminServer.class, args);
    }

    @Bean
    public CommandLineRunner demo(InitialDataLoader initialDataLoader) {
        return args -> {
//            initialDataLoader.run(args); // Execute the InitialDataLoader
        };
    }

}
