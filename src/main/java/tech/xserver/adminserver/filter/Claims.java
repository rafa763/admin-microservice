package tech.xserver.adminserver.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.xserver.adminserver.service.TokenService;

import java.util.List;

@Slf4j
@Service
public class Claims {
    private final TokenService tokenService;

    public Claims(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public Long getClaims(String token, String claim) {
        // convert token to DecodedJWT
        DecodedJWT tokenDecoded = tokenService.verifyToken(token);
        log.warn("Decoded token: " + tokenDecoded.getClaims());
        log.warn("Decoded token: " + tokenDecoded.getClaims());
        return tokenDecoded.getClaim(claim).asLong();
    }

    // extract role authority from token
    public List<String> getRoles(String token) {
        DecodedJWT tokenDecoded = tokenService.verifyToken(token);
        return tokenDecoded.getClaim("roles").asList(String.class);
    }
}
