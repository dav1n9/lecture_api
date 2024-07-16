package com.dav1n9.lectureapi.domain.like.controller;

import com.dav1n9.lectureapi.global.api.ApiResponse;
import com.dav1n9.lectureapi.global.security.UserDetailsImpl;
import com.dav1n9.lectureapi.domain.like.service.LikeService;
import com.dav1n9.lectureapi.global.api.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like", description = "강의 좋아요, 취소 API")
@RestController
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/lectures")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{lectureId}/like")
    @Operation(summary = "강의 좋아요 api")
    public ApiResponse<Object> likeLecture(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @PathVariable Long lectureId) {
        likeService.like(userDetails.getUser(), lectureId);
        return ApiUtils.SUCCESS("좋아요 성공", null);
    }

    @DeleteMapping("/{lectureId}/unlike")
    @Operation(summary = "강의 좋아요 취소 api")
    public ApiResponse<Void> unlikeLecture(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @PathVariable Long lectureId) {
        likeService.unlike(userDetails.getUser(), lectureId);
        return ApiUtils.SUCCESS("좋아요 취소 성공", null);
    }
}
