package com.dav1n9.lectureapi.domain.refreshToken;

import com.dav1n9.lectureapi.domain.member.entity.Member;
import com.dav1n9.lectureapi.domain.member.repository.MemberRepository;
import com.dav1n9.lectureapi.global.exception.ErrorType;
import com.dav1n9.lectureapi.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    /**
     * refresh token 이 유효하고 동일한지 확인한다.
     * 만약 올바른 토큰이라면 access token 을 발급하고,
     * 그렇지 않다면 refresh 토큰 삭제 후 다시 로그인하도록 응답한다.
     * @param refreshToken 확인할 refresh token
     * @return 유효한 refresh token 이라면 새로운 access token 반환
     */
    @Transactional
    public String getAccess(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException(ErrorType.EXPIRED_REFRESH_TOKEN.getMessage()));

        if (!token.getRefreshToken().equals(refreshToken)) {
            refreshTokenRepository.delete(token);
            throw new NullPointerException(ErrorType.NOT_VALID_REFRESH_TOKEN.getMessage());
        }

        Member member = memberRepository.findById(token.getMemberId())
                .orElseThrow(() -> new NullPointerException(ErrorType.NOT_FOUND_MEMBER.getMessage()));
        return jwtUtil.createAccessToken(member.getEmail(), member.getRole());
    }
}
