package com.dav1n9.lectureapi.domain.comment.controller;

import com.dav1n9.lectureapi.global.api.ApiResponse;
import com.dav1n9.lectureapi.domain.comment.dto.CommentRequest;
import com.dav1n9.lectureapi.domain.comment.dto.CommentResponse;
import com.dav1n9.lectureapi.global.security.UserDetailsImpl;
import com.dav1n9.lectureapi.domain.comment.service.CommentService;
import com.dav1n9.lectureapi.global.api.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "무한 댓글 작성, 수정 및 삭제 API")
@RestController
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/lectures/{lectureId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "댓글 작성 api")
    public ApiResponse<CommentResponse> saveComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @PathVariable Long lectureId,
                                                    @RequestBody CommentRequest request) {
        return ApiUtils.SUCCESS(null, commentService.saveComment(userDetails.getUser(), lectureId, request));
    }

    @PostMapping("/{commentId}/replies")
    @Operation(summary = "대댓글 작성 api")
    public ApiResponse<CommentResponse> saveReply(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable Long lectureId,
                                                  @PathVariable Long commentId,
                                                  @RequestBody CommentRequest request) {
        return ApiUtils.SUCCESS(null, commentService.saveReply(userDetails.getUser(), lectureId, commentId, request));
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정 api")
    public ApiResponse<CommentResponse> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable Long lectureId,
                                                      @PathVariable Long commentId,
                                                      @RequestBody CommentRequest request) {
        return ApiUtils.SUCCESS(null, commentService.update(userDetails.getUser(), lectureId, commentId, request));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제 api")
    public ApiResponse<Long> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @PathVariable Long lectureId,
                                           @PathVariable Long commentId) {
        return ApiUtils.SUCCESS(null, commentService.delete(userDetails.getUser(), lectureId, commentId));
    }
}
