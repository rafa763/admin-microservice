package tech.xserver.adminserver.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.xserver.adminserver.DTO.RegisterDto;
import tech.xserver.adminserver.entity.Role;
import tech.xserver.adminserver.entity.RoleEntity;
import tech.xserver.adminserver.entity.UserEntity;
import tech.xserver.adminserver.filter.CustomUserDetails;
import tech.xserver.adminserver.mappers.UserMapper;
import tech.xserver.adminserver.repo.RoleRepo;
import tech.xserver.adminserver.repo.UserRepo;
import tech.xserver.adminserver.repo.ViewsRepo;
import tech.xserver.adminserver.repo.VotesRepo;
import tech.xserver.adminserver.service.RoleService;
import tech.xserver.adminserver.service.TokenService;
import tech.xserver.adminserver.service.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;
    private final UserMapper userMapper;
    private final ViewsRepo viewsRepo;
    private final VotesRepo votesRepo;

    public UserServiceImpl(UserRepo userRepository, RoleService roleService, PasswordEncoder encoder, TokenService tokenService, UserMapper userMapper, ViewsRepo viewsRepo, VotesRepo votesRepo) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
        this.viewsRepo = viewsRepo;
        this.votesRepo = votesRepo;
    }
    @Override
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void addRoleToUser(Long userId, Role role) {
        Optional<UserEntity> user = userRepository.findById(userId);
        Optional<RoleEntity> roleEntity = roleService.getRoleByName(role.toString());
        if (user.isPresent() && roleEntity.isPresent()) {
            user.get().getRoles().add(roleEntity.get());
            userRepository.save(user.get());
        }
    }

    @Override
    public UserEntity saveUser(RegisterDto user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dob = LocalDate.parse(user.getDob(), formatter);
        LocalDate now = LocalDate.now();
        float age = now.getYear() - dob.getYear();
        UserEntity userEntity = userMapper.mapToUserEntityFromRegister(user);
        if (age > 18) {
            userEntity.getRoles().add(roleService.getRoleByName(Role.USER.toString()).get());
        } else {

            userEntity.getRoles().add(roleService.getRoleByName(Role.CHILD.toString()).get());
        }
        userEntity.setPassword(encoder.encode(user.getPassword()));
        userEntity.setCreated_at(LocalDate.now());
        userEntity.setActive(true);
        userEntity.setDob(dob);
        UserEntity d = userRepository.save(userEntity);
        log.info("User saved: " + d);
        return d;
    }

    @Override
    public Optional<UserEntity> updateUser(Long id, UserEntity user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        viewsRepo.deleteByUserId(id);
        votesRepo.deleteByUserId(id);
    }

    @Override
    public void reissueToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            String token = tokenService.extractToken(request);
            System.out.println("Token: " + token);
            DecodedJWT decodedJWT = tokenService.verifyToken(token);
            String email = decodedJWT.getSubject();
            CustomUserDetails user = (CustomUserDetails) loadUserByUsername(email);
            String accessToken = tokenService.generateAccessToken(user);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            response.getWriter().write(new ObjectMapper().writeValueAsString(tokens));

//            response.setHeader("access_token", accessToken);
        } catch (Exception exception) {
            throw new RuntimeException("Refresh token is invalid");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
       Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        // add user id to the token
        return new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword(), authorities);
//       return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
