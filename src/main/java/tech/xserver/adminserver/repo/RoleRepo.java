package tech.xserver.adminserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.xserver.adminserver.entity.RoleEntity;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
