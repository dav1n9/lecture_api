package com.dav1n9.lectureapi.domain.teacher.repository;

import com.dav1n9.lectureapi.domain.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
