package com.dav1n9.lectureapi.entity;

import com.dav1n9.lectureapi.dto.CommentRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @Column(name = "re_parent")
    private Long parent;

    @Column(name = "re_order")
    private Long order;

    @Column(name = "re_depth")
    private Integer depth;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Builder
    public Comment(String content, Long parent, Long order, Integer depth, Member member, Lecture lecture) {
        this.content = content;
        this.parent = parent;
        this.order = order;
        this.depth = depth;
        this.member = member;
        this.lecture = lecture;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public void update(CommentRequest request) {
        this.content = request.getContent();
    }
}
