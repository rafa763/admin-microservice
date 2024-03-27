package tech.xserver.adminserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tech.xserver.adminserver.entity.RoleEntity;
import tech.xserver.adminserver.entity.UserEntity;
import tech.xserver.adminserver.repo.RoleRepo;
import tech.xserver.adminserver.repo.UserRepo;

import java.util.Optional;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final RoleRepo roleRepository;
    private final UserRepo userRepository;
    private final PasswordEncoder encoder;

    public InitialDataLoader(RoleRepo roleRepository, UserRepo userRepository, PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        // Add roles
        Optional<RoleEntity> adminRole = roleRepository.findByName("ADMIN");
        if (adminRole.isEmpty()) {
            adminRole = Optional.of(new RoleEntity());
            adminRole.get().setName("ADMIN");
            roleRepository.save(adminRole.get());
        }

        // Check if CHILD role already exists
        Optional<RoleEntity> childRole = roleRepository.findByName("CHILD");
        if (childRole.isEmpty()) {
            childRole = Optional.of(new RoleEntity());
            childRole.get().setName("CHILD");
            roleRepository.save(childRole.get());
        }

        // Check if USER role already exists
        Optional<RoleEntity> userRole = roleRepository.findByName("USER");
        if (userRole.isEmpty()) {
            userRole = Optional.of(new RoleEntity());
            userRole.get().setName("USER");
            roleRepository.save(userRole.get());
        }

        // Check if ADMIN user already exists
        Optional<UserEntity> adminUser = userRepository.findByEmail("admin@me.com");
        if (adminUser.isEmpty()) {
            adminUser = Optional.of(new UserEntity());
            adminUser.get().setEmail("admin@me.com");
            adminUser.get().setPassword(encoder.encode("admin"));
            adminUser.get().setActive(true);
            adminUser.get().getRoles().add(adminRole.get()); // Assign ADMIN role
            userRepository.save(adminUser.get());
        }
    }
}
