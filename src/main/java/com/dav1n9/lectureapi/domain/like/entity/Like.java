package com.dav1n9.lectureapi.domain.like.entity;

import com.dav1n9.lectureapi.domain.member.entity.Member;
import com.dav1n9.lectureapi.domain.lecture.entity.Lecture;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Like(Lecture lecture, Member member) {
        this.lecture = lecture;
        this.member = member;
    }
}
