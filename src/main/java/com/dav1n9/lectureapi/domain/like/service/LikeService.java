package com.dav1n9.lectureapi.domain.like.service;

import com.dav1n9.lectureapi.domain.lecture.entity.Lecture;
import com.dav1n9.lectureapi.domain.like.entity.Like;
import com.dav1n9.lectureapi.domain.member.entity.Member;
import com.dav1n9.lectureapi.global.exception.ErrorType;
import com.dav1n9.lectureapi.domain.lecture.repository.LectureRepository;
import com.dav1n9.lectureapi.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LectureRepository lectureRepository;
    private final LikeRepository likeRepository;

    public void like(Member member, Long lectureId) {
        Lecture lecture = findLecture(lectureId);
        if (findLike(lecture, member).isPresent()) {
            throw new IllegalArgumentException(ErrorType.ALREADY_LIKED.getMessage());
        }
        likeRepository.save(new Like(lecture, member));
    }

    public void unlike(Member member, Long lectureId) {
        Lecture lecture = findLecture(lectureId);
        Optional<Like> like = findLike(lecture, member);
        if (like.isEmpty()) {
            throw new IllegalArgumentException(ErrorType.NOT_LIKED.getMessage());
        }
        likeRepository.delete(like.get());
    }

    private Lecture findLecture(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new NullPointerException(ErrorType.NOT_FOUND_LECTURE.getMessage()));
    }

    private Optional<Like> findLike(Lecture lecture, Member member) {
        return likeRepository.findByLectureAndMember(lecture, member);
    }
}
