package com.dav1n9.lectureapi.domain.member.controller;

import com.dav1n9.lectureapi.global.api.ApiResponse;
import com.dav1n9.lectureapi.domain.member.dto.MemberRequest;
import com.dav1n9.lectureapi.global.security.UserDetailsImpl;
import com.dav1n9.lectureapi.domain.member.service.MemberService;
import com.dav1n9.lectureapi.global.api.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "회원 가입, 로그인, 회원 탈퇴 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 api")
    public ApiResponse<Void> signup(@RequestBody MemberRequest request) {
        memberService.signup(request);
        return ApiUtils.SUCCESS("회원가입 성공", null);
    }

    @DeleteMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @Operation(summary = "회원탈퇴 api")
    public ApiResponse<Void> deleteAccount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        memberService.delete(userDetails);
        return ApiUtils.SUCCESS("회원탈퇴 성공", null);
    }
}
