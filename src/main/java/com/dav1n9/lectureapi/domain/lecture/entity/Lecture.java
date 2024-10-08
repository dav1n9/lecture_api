package com.dav1n9.lectureapi.domain.lecture.entity;

import com.dav1n9.lectureapi.domain.comment.entity.Comment;
import com.dav1n9.lectureapi.domain.like.entity.Like;
import com.dav1n9.lectureapi.domain.teacher.entity.Teacher;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "lectures")
@EntityListeners(AuditingEntityListener.class)
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    private String title;

    private Integer price;

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @Builder
    public Lecture(String title, Integer price, String description, Category category, Teacher teacher) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.teacher = teacher;
    }
}
