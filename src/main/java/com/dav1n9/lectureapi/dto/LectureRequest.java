package com.dav1n9.lectureapi.dto;

import com.dav1n9.lectureapi.entity.Category;
import com.dav1n9.lectureapi.entity.Lecture;
import com.dav1n9.lectureapi.entity.Teacher;
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
