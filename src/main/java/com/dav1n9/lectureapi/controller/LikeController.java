package com.dav1n9.lectureapi.controller;

import com.dav1n9.lectureapi.security.UserDetailsImpl;
import com.dav1n9.lectureapi.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/lectures")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{lectureId}/like")
    public ResponseEntity<Void> likeLecture(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @PathVariable Long lectureId) {
        likeService.like(userDetails.getUser(), lectureId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{lectureId}/unlike")
    public ResponseEntity<Void> unlikeLecture(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @PathVariable Long lectureId) {
        likeService.unlike(userDetails.getUser(), lectureId);
        return ResponseEntity.ok().build();
    }
}
