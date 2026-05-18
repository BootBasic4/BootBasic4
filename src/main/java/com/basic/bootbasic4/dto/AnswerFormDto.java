package com.basic.bootbasic4.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AnswerFormDto {

    // 등록/조회/수정
    @NotBlank(message="답변 내용 입력해주세요")
    private String content;

    // 등록/조회
    private Long questionId;
    private Long memberId;

    // 조회시에 필요
    private Long answerId;
    private String nickname;
    private String petType;
    private Integer petStarted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}