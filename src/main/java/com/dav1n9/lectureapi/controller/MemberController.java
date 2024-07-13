package com.dav1n9.lectureapi.controller;

import com.dav1n9.lectureapi.dto.MemberRequest;
import com.dav1n9.lectureapi.security.UserDetailsImpl;
import com.dav1n9.lectureapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody MemberRequest request) {
        memberService.signup(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        memberService.delete(userDetails);
        return ResponseEntity.ok().build();
    }
}
