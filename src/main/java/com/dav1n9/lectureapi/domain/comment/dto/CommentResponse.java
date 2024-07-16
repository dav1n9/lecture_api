package com.dav1n9.lectureapi.domain.comment.dto;

import com.dav1n9.lectureapi.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class CommentResponse {
    private final Long id;
    private final String content;
    private final Long parent;
    private final Long order;
    private final Integer depth;
    private final LocalDateTime createdAt;
    private final String memberEmail;
    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.parent = comment.getParent();
        this.order = comment.getOrder();
        this.depth = comment.getDepth();
        this.createdAt = comment.getCreatedAt();
        this.memberEmail = comment.getMember().getEmail();
    }
}
