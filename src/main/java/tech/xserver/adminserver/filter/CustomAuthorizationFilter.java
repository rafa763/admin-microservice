package tech.xserver.adminserver.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.xserver.adminserver.service.TokenService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    public CustomAuthorizationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/auth/login") || request.getServletPath().equals("/auth/refresh") || request.getServletPath().equals("/auth/register")) {
            filterChain.doFilter(request, response);
        } else {
            try {
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null) {
                    String token = tokenService.extractToken(request);
                    log.info("extracted Token: " + token);
                    DecodedJWT decodedJWT = tokenService.verifyToken(token);
                    String email = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<GrantedAuthority> authorities = Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    request.setAttribute("accessToken", token);
                }
                filterChain.doFilter(request, response);
            }
           // return 403 Forbidden if token is invalid, and 401 Unauthorized if token is expired

            catch (Exception e) {

                log.error("Error in CustomAuthorizationFilter: " + e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}