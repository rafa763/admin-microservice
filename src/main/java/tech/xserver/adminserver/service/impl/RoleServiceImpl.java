package tech.xserver.adminserver.service.impl;

import org.springframework.stereotype.Service;
import tech.xserver.adminserver.entity.RoleEntity;
import tech.xserver.adminserver.repo.RoleRepo;
import tech.xserver.adminserver.service.RoleService;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }
    @Override
    public List<RoleEntity> getRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Optional<RoleEntity> getRoleByName(String name) {
        return roleRepo.findByName(name);
    }

    @Override
    public Optional<RoleEntity> getRole(Long id) {
        return roleRepo.findById(id);
    }

    @Override
    public Optional<RoleEntity> updateRole(Long id, RoleEntity roleEntity) {
        Optional<RoleEntity> role = roleRepo.findById(id);
        if (role.isPresent()) {
            role.get().setName(roleEntity.getName());
            return Optional.of(roleRepo.save(role.get()));
        }
        return Optional.empty();
    }

    @Override
    public RoleEntity addRole(RoleEntity roleEntity) {
        return roleRepo.save(roleEntity);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepo.deleteById(id);
    }
}
