package tech.xserver.adminserver.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import tech.xserver.adminserver.DTO.RegisterDto;
import tech.xserver.adminserver.entity.Role;
import tech.xserver.adminserver.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> getUserById(Long id);
    Optional<UserEntity> getUserByEmail(String email);
    void addRoleToUser(Long userId, Role role);
    UserEntity saveUser(RegisterDto user);
    void reissueToken(HttpServletRequest request, HttpServletResponse response);
    UserDetails loadUserByUsername(String email);
    Optional<UserEntity> updateUser(Long id, UserEntity user);
    void deleteUser(Long id);


}
