package tech.xserver.adminserver.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.User;
import tech.xserver.adminserver.entity.UserEntity;
import tech.xserver.adminserver.filter.CustomUserDetails;

public interface TokenService {
    String generateAccessToken(CustomUserDetails user);
    String generateRefreshToken(CustomUserDetails user);
    DecodedJWT verifyToken(String token);
//    void reissueToken(HttpServletRequest request, HttpServletResponse response);
    String extractToken(HttpServletRequest request);

}
