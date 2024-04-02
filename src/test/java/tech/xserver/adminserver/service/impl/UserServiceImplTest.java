package tech.xserver.adminserver.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.xserver.adminserver.DTO.RegisterDto;
import tech.xserver.adminserver.entity.Role;
import tech.xserver.adminserver.entity.RoleEntity;
import tech.xserver.adminserver.entity.UserEntity;
import tech.xserver.adminserver.mappers.UserMapper;
import tech.xserver.adminserver.repo.UserRepo;
import tech.xserver.adminserver.repo.ViewsRepo;
import tech.xserver.adminserver.repo.VotesRepo;
import tech.xserver.adminserver.service.RoleService;
import tech.xserver.adminserver.service.TokenService;
import tech.xserver.adminserver.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepo userRepository;
    private UserService underTest;
    @Mock
    private RoleService roleService;
    @Mock
    private ViewsRepo viewsRepo;
    @Mock
    private VotesRepo votesRepo;

    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository, roleService, null, null, null, viewsRepo, votesRepo);
    }

    @Test
    void getUserById() {
        underTest.getUserById(anyLong());
        verify(userRepository).findById(anyLong());
    }

    @Test
    void getUserByEmail() {
        underTest.getUserByEmail(anyString());
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    @Disabled
    void addRoleToUser() {
        Long userId = 1L;
        Role role = Role.ADMIN;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setName(role.toString());
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(userEntity));
        when(roleService.getRoleByName(role.toString())).thenReturn(java.util.Optional.of(roleEntity));
        underTest.addRoleToUser(userId, role);
        verify(userRepository).save(userEntity);
    }

    @Test
    @Disabled
    void saveUser() {

    }

    @Test
    @Disabled
    void updateUser() {
    }

    @Test
    void deleteUser() {
        underTest.deleteUser(anyLong());
        verify(userRepository).deleteById(anyLong());
        verify(viewsRepo).deleteByUserId(anyLong());
        verify(votesRepo).deleteByUserId(anyLong());
    }

    @Test
    @Disabled
    void reissueToken() {
    }

    @Test
    @Disabled
    void loadUserByUsername() {

    }
}