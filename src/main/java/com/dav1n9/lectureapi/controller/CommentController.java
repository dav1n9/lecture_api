package com.dav1n9.lectureapi.controller;

import com.dav1n9.lectureapi.dto.ApiResponse;
import com.dav1n9.lectureapi.dto.CommentRequest;
import com.dav1n9.lectureapi.dto.CommentResponse;
import com.dav1n9.lectureapi.security.UserDetailsImpl;
import com.dav1n9.lectureapi.service.CommentService;
import com.dav1n9.lectureapi.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/lectures/{lectureId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponse<CommentResponse> saveComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable Long lectureId,
                                                    @RequestBody CommentRequest request) {
        return ApiUtils.SUCCESS(null, commentService.saveComment(userDetails.getUser(), lectureId, request));
    }

    @PostMapping("/{commentId}/replies")
    public ApiResponse<CommentResponse> saveReply(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable Long lectureId,
                                                  @PathVariable Long commentId,
                                                  @RequestBody CommentRequest request) {
        return ApiUtils.SUCCESS(null, commentService.saveReply(userDetails.getUser(), lectureId, commentId, request));
    }

    @PutMapping("/{commentId}")
    public ApiResponse<CommentResponse> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable Long lectureId,
                                                      @PathVariable Long commentId,
                                                      @RequestBody CommentRequest request) {
        return ApiUtils.SUCCESS(null, commentService.update(userDetails.getUser(), lectureId, commentId, request));
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Long> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @PathVariable Long lectureId,
                                           @PathVariable Long commentId) {
        return ApiUtils.SUCCESS(null, commentService.delete(userDetails.getUser(), lectureId, commentId));
    }
}
