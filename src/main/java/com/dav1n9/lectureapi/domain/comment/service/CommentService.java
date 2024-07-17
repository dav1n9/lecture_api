package com.dav1n9.lectureapi.domain.comment.service;

import com.dav1n9.lectureapi.domain.comment.dto.CommentRequest;
import com.dav1n9.lectureapi.domain.comment.dto.CommentResponse;
import com.dav1n9.lectureapi.domain.comment.entity.Comment;
import com.dav1n9.lectureapi.domain.lecture.entity.Lecture;
import com.dav1n9.lectureapi.domain.member.entity.Member;
import com.dav1n9.lectureapi.global.exception.ErrorType;
import com.dav1n9.lectureapi.domain.comment.repository.CommentRepository;
import com.dav1n9.lectureapi.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final LectureRepository lectureRepository;

    /**
     * 댓글을 저장하는 메소드
     * @param member 댓글을 작성한 사용자
     * @param lectureId 댓글을 작성한 강의
     * @param request 댓글 내용이 담긴 객체
     * @return 저장된 댓글 정보
     */
    public CommentResponse saveComment(Member member, Long lectureId, CommentRequest request) {
        Lecture lecture = findLecture(lectureId);

        long maxOrder = commentRepository.count();
        if (maxOrder == 0)
            return new CommentResponse(commentRepository.save(request.toComment(lecture, 1L, 1L, member)));

        Comment maxParent = commentRepository.findFirstByLectureOrderByParentDesc(lecture)
                .orElseThrow(() -> new NullPointerException(ErrorType.NOT_FOUND_COMMENT.getMessage()));

        return new CommentResponse(commentRepository
                .save(request.toComment(lecture, maxParent.getParent() + 1, maxOrder + 1, member)));
    }

    /**
     * 대댓글을 저장하는 메소드.
     * 어떤 댓글의 대댓글인지에 대한 정보가 필요하다.
     * @param member 댓글을 작성한 사용자
     * @param lectureId 댓글을 작성한 강의
     * @param commentId 작성한 대댓글의 부모 댓글 아이디
     * @param request 댓글 내용이 담긴 객체
     * @return 저장된 댓글 정보
     */
    @Transactional
    public CommentResponse saveReply(Member member, Long lectureId, Long commentId, CommentRequest request) {
        Lecture lecture = findLecture(lectureId);
        Comment parentComment = findComment(commentId);
        List<Comment> comments = commentRepository
                .findByParentAndOrderGreaterThanEqualOrderByOrderAsc(parentComment.getParent(), parentComment.getOrder());

        Comment reply;
        long order;
        Comment lastComment = comments.get(0);
        for (int i = 1; i < comments.size(); i++) {
            if (comments.get(i).getDepth() > parentComment.getDepth()) {
                lastComment = comments.get(i);
            } else break;
        }
        order = lastComment.getOrder() + 1;
        reply = request.toReply(lecture, parentComment.getParent(), order, parentComment.getDepth() + 1, member);

        List<Comment> others = commentRepository.findByOrderGreaterThanEqualOrderByOrderAsc(order);
        for (Comment comment : others) {
            comment.setOrder(++order);
        }

        return new CommentResponse(commentRepository.save(reply));
    }

    @Transactional
    public CommentResponse update(Member member, Long lectureId, Long commentId, CommentRequest request) {
        Lecture lecture = findLecture(lectureId);
        Comment comment = findComment(commentId);
        if (!comment.getMember().getEmail().equals(member.getEmail())) {
            throw new IllegalArgumentException(ErrorType.NOT_AUTHORIZED_TO_DELETE.getMessage());
        }
        comment.update(request);

        return new CommentResponse(comment);
    }

    @Transactional
    public Long delete(Member member, Long lectureId, Long commentId) {
        Lecture lecture = findLecture(lectureId);
        Comment comment = findComment(commentId);
        if (!comment.getMember().getEmail().equals(member.getEmail())) {
            throw new IllegalArgumentException(ErrorType.NOT_AUTHORIZED_TO_MODIFY.getMessage());
        }

        List<Comment> comments = commentRepository
                .findByParentAndOrderGreaterThanEqualOrderByOrderAsc(comment.getParent(), comment.getOrder());
        int depth = comments.get(0).getDepth();

        commentRepository.delete(comments.get(0));
        for (int i = 1; i < comments.size(); i++) {
            if (depth < comments.get(i).getDepth()) {
                commentRepository.delete(comments.get(i));
            } else break;
        }

        return commentId;
    }

    private Lecture findLecture(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new NullPointerException(ErrorType.NOT_FOUND_LECTURE.getMessage()));
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException(ErrorType.NOT_FOUND_COMMENT.getMessage()));
    }
}
