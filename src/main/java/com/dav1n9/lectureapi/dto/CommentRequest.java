package com.dav1n9.lectureapi.dto;

import com.dav1n9.lectureapi.entity.Comment;
import com.dav1n9.lectureapi.entity.Lecture;
import lombok.Getter;

@Getter
public class CommentRequest {

    private String content;

    public Comment toComment(Lecture lecture, Long parent, Long order) {
        return Comment.builder()
                .content(content)
                .parent(parent)
                .order(order)
                .depth(1)
                .lecture(lecture)
                .build();
    }

    public Comment toReply(Lecture lecture, Long parent, Long order, Integer depth) {
        return Comment.builder()
                .content(content)
                .parent(parent)
                .order(order)
                .depth(depth)
                .lecture(lecture)
                .build();
    }
}
