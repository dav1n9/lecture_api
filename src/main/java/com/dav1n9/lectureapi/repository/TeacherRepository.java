package com.dav1n9.lectureapi.repository;

import com.dav1n9.lectureapi.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
