package com.dav1n9.lectureapi.service;

import com.dav1n9.lectureapi.entity.Lecture;
import com.dav1n9.lectureapi.entity.Like;
import com.dav1n9.lectureapi.entity.Member;
import com.dav1n9.lectureapi.repository.LectureRepository;
import com.dav1n9.lectureapi.repository.LikeRepository;
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
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }
        likeRepository.save(new Like(lecture, member));
    }

    public void unlike(Member member, Long lectureId) {
        Lecture lecture = findLecture(lectureId);
        Optional<Like> like = findLike(lecture, member);
        if (like.isEmpty()) {
            throw new IllegalArgumentException("좋아요를 누르지 않았습니다.");
        }
        likeRepository.delete(like.get());
    }

    private Lecture findLecture(Long lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow(NullPointerException::new);
    }

    private Optional<Like> findLike(Lecture lecture, Member member) {
        return likeRepository.findByLectureAndMember(lecture, member);
    }
}
