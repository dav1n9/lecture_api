package com.dav1n9.lectureapi.domain.like.repository;

import com.dav1n9.lectureapi.domain.lecture.entity.Lecture;
import com.dav1n9.lectureapi.domain.like.entity.Like;
import com.dav1n9.lectureapi.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByLectureAndMember(Lecture lecture, Member member);
}
