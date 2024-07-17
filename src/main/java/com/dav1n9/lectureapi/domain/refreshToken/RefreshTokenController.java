package com.dav1n9.lectureapi.domain.refreshToken;

import com.dav1n9.lectureapi.global.api.ApiResponse;
import com.dav1n9.lectureapi.global.api.ApiUtils;
import com.dav1n9.lectureapi.global.exception.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<Void>> refresh(HttpServletRequest request) {
        String refreshToken = validateHeader(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, refreshTokenService.getAccess(refreshToken))
                .body(ApiUtils.SUCCESS("access 토큰이 발급되었습니다.", null));
    }

    private String validateHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshTokenHeader = request.getHeader("RefreshToken");
        if (authorizationHeader.isEmpty()|| refreshTokenHeader.isEmpty()) {
            throw new NullPointerException(ErrorType.NOT_FOUND_TOKEN.getMessage());
        }
        return refreshTokenHeader;
    }
}
