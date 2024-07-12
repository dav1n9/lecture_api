package com.dav1n9.lectureapi.dto;

import com.dav1n9.lectureapi.entity.Category;
import com.dav1n9.lectureapi.entity.Lecture;
import com.dav1n9.lectureapi.entity.Teacher;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LectureResponse {
    private final Long id;
    private final String title;
    private final Integer price;
    private final String description;
    private final Category category;
    private final LocalDateTime createdAt;
    private final TeacherDTO teacher;
    public LectureResponse(Lecture lecture) {
        this.id = lecture.getId();
        this.title = lecture.getTitle();
        this.price = lecture.getPrice();
        this.description = lecture.getDescription();
        this.category = lecture.getCategory();
        this.createdAt = lecture.getCreatedAt();
        this.teacher = new TeacherDTO(lecture.getTeacher());
    }
    @Getter
    private static class TeacherDTO {
        private final Long id;
        private final String name;
        private final Integer career;
        private final String company;
        private final String biography;
        private final LocalDateTime createdAt;

        public TeacherDTO(Teacher teacher) {
            this.id = teacher.getId();
            this.name = teacher.getName();
            this.career = teacher.getCareer();
            this.company = teacher.getCompany();
            this.biography = teacher.getBiography();
            this.createdAt = teacher.getCreatedAt();
        }
    }
}
