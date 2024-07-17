package com.dav1n9.lectureapi.global.config;

import com.dav1n9.lectureapi.domain.refreshToken.RefreshTokenRepository;
import com.dav1n9.lectureapi.global.api.ApiResponse;
import com.dav1n9.lectureapi.global.exception.ErrorType;
import com.dav1n9.lectureapi.global.jwt.JwtUtil;
import com.dav1n9.lectureapi.global.security.JwtAuthenticationFilter;
import com.dav1n9.lectureapi.global.security.JwtAuthorizationFilter;
import com.dav1n9.lectureapi.global.security.UserDetailsServiceImpl;
import com.dav1n9.lectureapi.global.api.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final RefreshTokenRepository refreshTokenRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, refreshTokenRepository);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/members/**", "/lectures/**", "/refresh",
                                "/swagger/**", "/swagger.html", "/swagger-ui/**", "/api-docs/**").permitAll()
                        .anyRequest().authenticated()
        );

        http.exceptionHandling((exceptionConfig) ->
                exceptionConfig.accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(ErrorType.ACCESS_DENIED.getCode());
                    response.setContentType("application/json; charset=UTF-8");
                    ApiResponse<Object> error = new ApiResponse<>(ResponseStatus.FAILURE, ErrorType.ACCESS_DENIED.getMessage(), null);
                    response.getWriter().write(new ObjectMapper().writeValueAsString(error));
                })
        );

        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}