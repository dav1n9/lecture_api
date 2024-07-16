package com.dav1n9.lectureapi.domain.lecture.dto;

import com.dav1n9.lectureapi.domain.lecture.entity.Category;
import com.dav1n9.lectureapi.domain.lecture.entity.Lecture;
import com.dav1n9.lectureapi.domain.teacher.entity.Teacher;
import lombok.Getter;

@Getter
public class LectureRequest {
    private String title;
    private Integer price;
    private String description;
    private Category category;
    private Long teacherId;

    public Lecture toEntity(Teacher teacher) {
        return Lecture.builder()
                .title(title)
                .price(price)
                .description(description)
                .category(category)
                .teacher(teacher)
                .build();
    }
}
