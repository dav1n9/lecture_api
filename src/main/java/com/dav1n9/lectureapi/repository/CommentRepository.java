package com.dav1n9.lectureapi.repository;

import com.dav1n9.lectureapi.entity.Comment;
import com.dav1n9.lectureapi.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findFirstByLectureOrderByParentDesc(Lecture lecture);
    List<Comment> findByParentAndOrderGreaterThanEqualOrderByOrderAsc(Long parent, Long order);
    List<Comment> findByOrderGreaterThanEqualOrderByOrderAsc(Long order);
}
