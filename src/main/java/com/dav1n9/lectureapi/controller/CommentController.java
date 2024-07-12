package com.dav1n9.lectureapi.controller;

import com.dav1n9.lectureapi.dto.CommentRequest;
import com.dav1n9.lectureapi.dto.CommentResponse;
import com.dav1n9.lectureapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lectures/{lectureId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> saveComment(@PathVariable Long lectureId,
                                                       @RequestBody CommentRequest request) {
        return ResponseEntity.ok()
                .body(commentService.saveComment(lectureId, request));
    }

    @PostMapping("/{commentId}/replies")
    public ResponseEntity<CommentResponse> saveReply(@PathVariable Long lectureId,
                                                     @PathVariable Long commentId,
                                                     @RequestBody CommentRequest request) {
        return ResponseEntity.ok()
                .body(commentService.saveReply(lectureId, commentId, request));
    }

    @PutMapping ("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long lectureId,
                                                         @PathVariable Long commentId,
                                                         @RequestBody CommentRequest request) {
        return ResponseEntity.ok()
                .body(commentService.update(lectureId, commentId, request));
    }

    @DeleteMapping ("/{commentId}")
    public ResponseEntity<Long> deleteComment(@PathVariable Long lectureId,
                                                         @PathVariable Long commentId) {
        return ResponseEntity.ok()
                .body(commentService.delete(lectureId, commentId));
    }
}
