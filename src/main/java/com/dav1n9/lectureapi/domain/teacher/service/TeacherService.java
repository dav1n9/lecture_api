package com.dav1n9.lectureapi.domain.teacher.service;

import com.dav1n9.lectureapi.domain.teacher.dto.TeacherRequest;
import com.dav1n9.lectureapi.domain.teacher.dto.TeacherResponse;
import com.dav1n9.lectureapi.domain.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    
    private final TeacherRepository teacherRepository;

    public TeacherResponse save(TeacherRequest request) {
        return new TeacherResponse(teacherRepository.save(request.toEntity()));
    }
}
