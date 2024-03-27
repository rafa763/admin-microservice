//package tech.xserver.adminserver.service.impl;
//
//import org.springframework.stereotype.Service;
//import tech.xserver.adminserver.DTO.UserDto;
//import tech.xserver.adminserver.entity.SessionEntity;
//import tech.xserver.adminserver.entity.UserEntity;
//import tech.xserver.adminserver.service.EmailService;
//import tech.xserver.adminserver.service.SessionService;
//import tech.xserver.adminserver.service.TokenService;
//import tech.xserver.adminserver.service.UserService;
//
//import java.security.SecureRandom;
//import java.util.Optional;
//
//@Service
//public class MagicServiceImpl {
//
//    private final UserService userService;
//    private final SessionService sessionService;
//    private final TokenService tokenService;
//    private final EmailService emailService;
//
//    public MagicServiceImpl(
//            UserService userService,
//            SessionService sessionService,
//            TokenService tokenService,
//            EmailService emailService
//    ) {
//        this.userService = userService;
//        this.sessionService = sessionService;
//        this.tokenService = tokenService;
//        this.emailService = emailService;
//    }
//
//    private String idGenerator(Integer size) {
//       // generate a random alphanumeric string of length size securely
//        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        SecureRandom random = new SecureRandom(chars.getBytes());
//        return random.ints(size, 0, chars.length())
//                .mapToObj(i -> "" + chars.charAt(i))
//                .reduce("", String::concat);
//    }
//
//    public void login(String email) {
//        Optional<UserEntity> user = userService.getUserByEmail(email);
//        if (user.isPresent()) {
//            String sessionId = idGenerator(32);
//            String token = tokenService.generateToken(email);
//            SessionEntity session = new SessionEntity();
//            sessionService.saveSession(sessionId, user.get());
//            emailService.sendEmail(email, "Login", "Your session id is: " + sessionId);
//        }
//    }
//
//    public void register(UserEntity user) {
//        userService.saveUser(user);
//        emailService.sendEmail(user.getEmail(), "Register", "You have been registered");
//    }
//
//}
