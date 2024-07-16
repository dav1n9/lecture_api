package com.dav1n9.lectureapi.domain.lecture.service;

import com.dav1n9.lectureapi.domain.lecture.dto.LectureRequest;
import com.dav1n9.lectureapi.domain.lecture.dto.LectureResponse;
import com.dav1n9.lectureapi.domain.lecture.entity.Category;
import com.dav1n9.lectureapi.domain.lecture.entity.Lecture;
import com.dav1n9.lectureapi.domain.lecture.entity.SortField;
import com.dav1n9.lectureapi.domain.teacher.entity.Teacher;
import com.dav1n9.lectureapi.global.exception.ErrorType;
import com.dav1n9.lectureapi.domain.lecture.repository.LectureRepository;
import com.dav1n9.lectureapi.domain.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final TeacherRepository teacherRepository;

    public LectureResponse save(LectureRequest request) {
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new NullPointerException(ErrorType.NOT_FOUND_TEACHER.getMessage()));
        return new LectureResponse(lectureRepository.save(request.toEntity(teacher)));
    }

    public LectureResponse findById(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new NullPointerException(ErrorType.NOT_FOUND_LECTURE.getMessage()));
        return new LectureResponse(lecture);
    }

    public List<LectureResponse> findByCategory(Category category, SortField sort) {
        List<Lecture> lectures = lectureRepository.findByCategoryOrderByCreatedAtDesc(category);
        if (sort == null || sort == SortField.RECENT) {
            return lectures.stream().map(LectureResponse::new).toList();
        }

        switch (sort) {
            case TITLE -> lectures.sort(Comparator.comparing(Lecture::getTitle));
            case PRICE -> lectures.sort(Comparator.comparing(Lecture::getPrice));
            default -> throw new IllegalArgumentException(ErrorType.INVALID_SORT_PARAMETER.getMessage());
        }
        return lectures.stream().map(LectureResponse::new).toList();
    }
}
