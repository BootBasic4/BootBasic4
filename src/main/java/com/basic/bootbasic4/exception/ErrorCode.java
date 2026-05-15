package com.basic.bootbasic4.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 질문 관련
    QUESTION_NOT_FOUND("Q001", "해당 질문을 찾을 수 없습니다."),
    QUESTION_UNAUTHORIZED("Q002", "질문을 수정/삭제할 권한이 없습니다."),
    QUESTION_INVALID_CATEGORY("Q003", "유효하지 않은 카테고리입니다."),

    // 답변 관련
    ANSWER_NOT_FOUND("A001", "해당 답변을 찾을 수 없습니다."),
    ANSWER_UNAUTHORIZED("A002", "답변을 수정/삭제할 권한이 없습니다."),

    // 회원 관련
    MEMBER_NOT_FOUND("M001", "해당 회원을 찾을 수 없습니다."),
    MEMBER_NOT_LOGGED_IN("M002", "로그인이 필요합니다."),

    // 공통
    INVALID_INPUT("C001", "입력값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR("C002", "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");

    private final String code;
    private final String message;
}