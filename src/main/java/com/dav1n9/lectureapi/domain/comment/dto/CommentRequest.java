package com.dav1n9.lectureapi.domain.comment.dto;

import com.dav1n9.lectureapi.domain.comment.entity.Comment;
import com.dav1n9.lectureapi.domain.lecture.entity.Lecture;
import com.dav1n9.lectureapi.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class CommentRequest {

    private String content;

    public Comment toComment(Lecture lecture, Long parent, Long order, Member member) {
        return Comment.builder()
                .content(content)
                .parent(parent)
                .order(order)
                .depth(1)
                .lecture(lecture)
                .member(member)
                .build();
    }

    public Comment toReply(Lecture lecture, Long parent, Long order, Integer depth, Member member) {
        return Comment.builder()
                .content(content)
                .parent(parent)
                .order(order)
                .depth(depth)
                .lecture(lecture)
                .member(member)
                .build();
    }
}
