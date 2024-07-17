package com.dav1n9.lectureapi.global.security;

import com.dav1n9.lectureapi.global.api.ApiResponse;
import com.dav1n9.lectureapi.global.exception.ErrorType;
import com.dav1n9.lectureapi.global.jwt.JwtUtil;
import com.dav1n9.lectureapi.global.api.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.getAccessTokenFromHeader(req);
        String refreshToken = jwtUtil.getRefreshTokenFromHeader(req);

        if (StringUtils.hasText(accessToken)) {

            String validateToken = jwtUtil.validateToken(accessToken);
            if (validateToken != null) {
                log.error("Token Error");
                if (!refreshToken.isEmpty()){
                    filterChain.doFilter(req, res);
                    return;
                }
                setTokenError(res, validateToken);
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(accessToken);

            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                setTokenError(res, ErrorType.NOT_VALID_TOKEN.getMessage());
                return;
            }
        }

        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private void setTokenError(HttpServletResponse response, String message) {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(401);


        ApiResponse<Object> error = new ApiResponse<>(ResponseStatus.FAILURE, message, null);
        try {
            response.getWriter().write(new ObjectMapper().writeValueAsString(error));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}