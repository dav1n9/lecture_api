package com.dav1n9.lectureapi.controller;

import com.dav1n9.lectureapi.dto.ApiResponse;
import com.dav1n9.lectureapi.dto.MemberRequest;
import com.dav1n9.lectureapi.security.UserDetailsImpl;
import com.dav1n9.lectureapi.service.MemberService;
import com.dav1n9.lectureapi.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ApiResponse<Void> signup(@RequestBody MemberRequest request) {
        memberService.signup(request);
        return ApiUtils.SUCCESS("회원가입 성공", null);
    }

    @DeleteMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ApiResponse<Void> deleteAccount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        memberService.delete(userDetails);
        return ApiUtils.SUCCESS("회원탈퇴 성공", null);
    }
}
