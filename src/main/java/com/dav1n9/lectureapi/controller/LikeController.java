package com.dav1n9.lectureapi.controller;

import com.dav1n9.lectureapi.dto.ApiResponse;
import com.dav1n9.lectureapi.security.UserDetailsImpl;
import com.dav1n9.lectureapi.service.LikeService;
import com.dav1n9.lectureapi.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<Object> likeLecture(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @PathVariable Long lectureId) {
        likeService.like(userDetails.getUser(), lectureId);
        return ApiUtils.SUCCESS("좋아요 성공", null);
    }

    @DeleteMapping("/{lectureId}/unlike")
    public ApiResponse<Void> unlikeLecture(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @PathVariable Long lectureId) {
        likeService.unlike(userDetails.getUser(), lectureId);
        return ApiUtils.SUCCESS("좋아요 취소 성공", null);
    }
}
