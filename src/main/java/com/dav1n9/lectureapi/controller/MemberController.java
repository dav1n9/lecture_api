package com.dav1n9.lectureapi.controller;

import com.dav1n9.lectureapi.dto.MemberRequest;
import com.dav1n9.lectureapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
