package com.dav1n9.lectureapi.service;

import com.dav1n9.lectureapi.dto.MemberRequest;
import com.dav1n9.lectureapi.entity.Member;
import com.dav1n9.lectureapi.exception.ErrorType;
import com.dav1n9.lectureapi.repository.MemberRepository;
import com.dav1n9.lectureapi.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.dav1n9.lectureapi.entity.Member.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(MemberRequest request) {
        String email = request.getEmail();
        String password = passwordEncoder.encode(request.getPassword());

        // 회원 중복 확인
        Optional<Member> checkEmail = memberRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException(ErrorType.DUPLICATE_EMAIL_ERROR.getMessage());
        }

        // 사용자 등록
        Member member = builder()
                .email(email)
                .password(password)
                .gender(request.getGender())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .build();
        memberRepository.save(member);
    }

    @Transactional
    public void delete(UserDetailsImpl userDetails) {
        memberRepository.delete(userDetails.getUser());
    }
}