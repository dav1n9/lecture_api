package com.dav1n9.lectureapi.repository;

import com.dav1n9.lectureapi.entity.Category;
import com.dav1n9.lectureapi.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByCategoryOrderByCreatedAtDesc(Category category);
}
