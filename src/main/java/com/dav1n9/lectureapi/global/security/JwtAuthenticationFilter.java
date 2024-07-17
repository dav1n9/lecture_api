package com.dav1n9.lectureapi.global.security;

import com.dav1n9.lectureapi.domain.refreshToken.RefreshToken;
import com.dav1n9.lectureapi.domain.refreshToken.RefreshTokenRepository;
import com.dav1n9.lectureapi.global.api.ApiResponse;
import com.dav1n9.lectureapi.domain.member.dto.MemberRequest;
import com.dav1n9.lectureapi.domain.member.entity.Role;
import com.dav1n9.lectureapi.global.exception.ErrorType;
import com.dav1n9.lectureapi.global.jwt.JwtUtil;
import com.dav1n9.lectureapi.global.api.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        setFilterProcessesUrl("/members/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            MemberRequest requestDto = new ObjectMapper().readValue(request.getInputStream(), MemberRequest.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        Role role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
        Long id = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getId();

        String accessToken = jwtUtil.createAccessToken(email, role);
        String refreshToken = jwtUtil.createRefreshToken(id);

        apiResponse(ResponseStatus.SUCCESS, response, "로그인 성공");
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);  // 헤더에 토큰 담기
        response.addHeader("RefreshToken", refreshToken);

        refreshTokenRepository.save(new RefreshToken(refreshToken, id));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        apiResponse(ResponseStatus.FAILURE, response, ErrorType.FAIL_LOGIN.getMessage());
    }

    private void apiResponse(ResponseStatus status, HttpServletResponse response, String message) {
        response.setContentType("application/json; charset=UTF-8");
        if (status == ResponseStatus.FAILURE)
            response.setStatus(401);

        ApiResponse<Object> error = new ApiResponse<>(status, message, null);
        try {
            response.getWriter().write(new ObjectMapper().writeValueAsString(error));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}