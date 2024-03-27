package tech.xserver.adminserver.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.xserver.adminserver.DTO.RegisterDto;
import tech.xserver.adminserver.DTO.SafeUserDto;
import tech.xserver.adminserver.DTO.UserDto;
import tech.xserver.adminserver.config.MailConfig;
import tech.xserver.adminserver.entity.UserEntity;
import tech.xserver.adminserver.filter.Claims;
import tech.xserver.adminserver.mappers.RoleMapper;
import tech.xserver.adminserver.mappers.UserMapper;
import tech.xserver.adminserver.model.Mail;
import tech.xserver.adminserver.service.TokenService;
import tech.xserver.adminserver.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final MailConfig mailConfig;
    private final Claims claims;

    public AuthController(UserService userService, UserMapper userMapper, MailConfig mailConfig, Claims claims) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.mailConfig = mailConfig;
        this.claims = claims;
    }

    @GetMapping("/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        userService.reissueToken(request, response);
    }

    @PostMapping("/register")
    public SafeUserDto register(@RequestBody RegisterDto registerDto) {
        log.info("Registering user: " + registerDto);

        UserEntity user = userService.saveUser(registerDto);
        mailConfig.sendMessage(new Mail(user.getEmail(), "Registration", "Thanks for your registration!"));
        return userMapper.mapToSafeUserDto(user);
    }

    @GetMapping("/account")
    public SafeUserDto getAccount(@RequestAttribute("accessToken") String accessToken) {
        Long id = claims.getClaims(accessToken, "uid");
        UserEntity user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.mapToSafeUserDto(user);
    }

    @GetMapping("/account/type")
    public SafeUserDto getAccountType(@RequestAttribute("accessToken") String accessToken) {
        Long id = claims.getClaims(accessToken, "uid");
        UserEntity user = userService.getUserById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return userMapper.mapToSafeUserDto(user);
    }

    @DeleteMapping("/account")
    public void deleteAccount(@RequestAttribute("accessToken") String accessToken) {
        Long id = claims.getClaims(accessToken, "uid");
        UserEntity user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userService.deleteUser(user.getId());
    }
}
