package com.dav1n9.lectureapi.service;

import com.dav1n9.lectureapi.dto.LectureRequest;
import com.dav1n9.lectureapi.dto.LectureResponse;
import com.dav1n9.lectureapi.entity.Category;
import com.dav1n9.lectureapi.entity.Lecture;
import com.dav1n9.lectureapi.entity.SortField;
import com.dav1n9.lectureapi.entity.Teacher;
import com.dav1n9.lectureapi.repository.LectureRepository;
import com.dav1n9.lectureapi.repository.TeacherRepository;
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
                .orElseThrow(NullPointerException::new);
        return new LectureResponse(lectureRepository.save(request.toEntity(teacher)));
    }

    public LectureResponse findById(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(NullPointerException::new);
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
            default -> throw new IllegalArgumentException("Invalid sort parameter");
        }
        return lectures.stream().map(LectureResponse::new).toList();
    }
}
