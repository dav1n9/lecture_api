package com.dav1n9.lectureapi.controller;

import com.dav1n9.lectureapi.dto.LectureRequest;
import com.dav1n9.lectureapi.dto.LectureResponse;
import com.dav1n9.lectureapi.entity.Category;
import com.dav1n9.lectureapi.entity.SortField;
import com.dav1n9.lectureapi.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<LectureResponse> saveLecture(@RequestBody LectureRequest request) {
        return ResponseEntity.ok()
                .body(lectureService.save(request));
    }

    @GetMapping("/{lectureId}")
    public ResponseEntity<LectureResponse> findLecture(@PathVariable Long lectureId) {
        return ResponseEntity.ok()
                .body(lectureService.findById(lectureId));
    }

    @GetMapping
    public ResponseEntity<List<LectureResponse>> findLecture(@RequestParam Category category,
                                                            @RequestParam(required = false) SortField sort) {
        return ResponseEntity.ok()
                .body(lectureService.findByCategory(category, sort));
    }

}
