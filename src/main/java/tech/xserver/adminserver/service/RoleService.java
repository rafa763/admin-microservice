package tech.xserver.adminserver.service;

import tech.xserver.adminserver.entity.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleEntity> getRoles();
    Optional<RoleEntity> getRole(Long id);
    Optional<RoleEntity> getRoleByName(String name);
    Optional<RoleEntity> updateRole(Long id, RoleEntity roleEntity);
    RoleEntity addRole(RoleEntity roleEntity);
    void deleteRole(Long id);
}
