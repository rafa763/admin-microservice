package tech.xserver.adminserver.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.xserver.adminserver.entity.RoleEntity;
import tech.xserver.adminserver.repo.RoleRepo;
import tech.xserver.adminserver.service.RoleService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    private RoleService underTest;
    @Mock
    private RoleRepo roleRepo;
    @BeforeEach
    void setUp() {
        underTest = new RoleServiceImpl(roleRepo);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getRoles() {
        underTest.getRoles();
        verify(roleRepo).findAll();
    }

    @Test
    void getRoleByName() {
        underTest.getRoleByName("name");
        verify(roleRepo).findByName("name");
    }

    @Test
    void getRole() {
        underTest.getRole(1L);
        verify(roleRepo).findById(1L);
    }

    @Test
    void updateRole() {
        Long id = 1L;
        String newName = "New Name";
        RoleEntity existingRole = new RoleEntity(id, "Old Name");
        Optional<RoleEntity> optionalRole = Optional.of(existingRole);
        Mockito.when(roleRepo.findById(id)).thenReturn(optionalRole);

        RoleEntity newRole = new RoleEntity(id, newName);
        Mockito.when(roleRepo.save(newRole)).thenReturn(newRole);

        Optional<RoleEntity> updatedRole = underTest.updateRole(id, newRole);
        assertThat(updatedRole).isPresent();
        assertThat(updatedRole.get().getName()).isEqualTo(newName);

        verify(roleRepo).findById(id);
        verify(roleRepo).save(newRole);
    }

    @Test
    void addRole() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setName("USER");
        underTest.addRole(roleEntity);
        verify(roleRepo).save(roleEntity);
    }

    @Test
    void deleteRole() {
        underTest.deleteRole(1L);
        verify(roleRepo).deleteById(1L);
    }
}