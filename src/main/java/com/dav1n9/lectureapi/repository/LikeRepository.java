package com.dav1n9.lectureapi.repository;

import com.dav1n9.lectureapi.entity.Lecture;
import com.dav1n9.lectureapi.entity.Like;
import com.dav1n9.lectureapi.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByLectureAndMember(Lecture lecture, Member member);
}
