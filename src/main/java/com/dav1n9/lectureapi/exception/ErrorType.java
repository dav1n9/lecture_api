package com.dav1n9.lectureapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {
    NOT_FOUND_MEMBER(404, "존재하지 않는 회원입니다."),
    NOT_FOUND_TEACHER(404, "존재하지 않는 강사입니다."),
    NOT_FOUND_LECTURE(404, "존재하지 않는 강의입니다."),
    NOT_FOUND_COMMENT(404, "존재하지 않는 댓글입니다."),
    ACCESS_ADMIN_ONLY(403, "해당 기능은 관리자만 접근 가능합니다."),
    DUPLICATE_EMAIL_ERROR(400, "이미 존재하는 이메일입니다."),
    PASSWORD_MISMATCH(401, "비밀번호가 일치하지 않습니다."),
    NOT_FOUND_TOKEN(404, "존재하지 않는 토큰입니다."),
    NOT_VALID_TOKEN(400, "잘못된 토큰입니다."),
    NOT_AUTHORIZED_TO_MODIFY(403, "본인이 작성한 글만 수정 가능합니다."),
    NOT_AUTHORIZED_TO_DELETE(403, "본인이 작성한 글만 삭제 가능합니다."),
    INVALID_SORT_PARAMETER(400, "해당 파라미터 기준으로 정렬할 수 없습니다."),
    ALREADY_LIKED(400, "이미 좋아요를 눌렀습니다."),
    NOT_LIKED(400, "좋아요를 누르지 않았습니다."),
    DUPLICATE_MEMBER(400, "중복된 사용자가 존재합니다.");

    private final int code;
    private final String message;
}