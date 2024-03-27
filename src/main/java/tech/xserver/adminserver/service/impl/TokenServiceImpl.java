package tech.xserver.adminserver.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import tech.xserver.adminserver.filter.CustomUserDetails;
import tech.xserver.adminserver.service.TokenService;
import tech.xserver.adminserver.service.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {
//    private final UserService userService;

//    public TokenServiceImpl(UserService userService) {
//        this.userService = userService;
//    }
    @Override
    public String generateAccessToken(CustomUserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256("superSecretCodeForJWT".getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                .withIssuer("xserver.tech")
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withClaim("uid", user.getUserId())
                .sign(algorithm);
    }

    @Override
    public String generateRefreshToken(CustomUserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256("superSecretCodeForJWT".getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 60 * 1000))
                .withIssuer("xserver.tech")
                .sign(algorithm);
    }

    @Override
    public DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("superSecretCodeForJWT".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    @Override
    public String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        } else {
            throw new RuntimeException("Token is missing");
        }
    }

//    @Override
//    public void reissueToken(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            String token = extractToken(request);
//            DecodedJWT decodedJWT = verifyToken(token);
//            String email = decodedJWT.getSubject();
//            User user = (User) userService.loadUserByUsername(email);
//            String accessToken = generateAccessToken(user);
////            Map<String, String> tokens = new HashMap<>();
//            response.setHeader("access_token", accessToken);
//        } catch (Exception exception) {
//            throw new RuntimeException("Refresh token is invalid");
//        }
//    }
}
