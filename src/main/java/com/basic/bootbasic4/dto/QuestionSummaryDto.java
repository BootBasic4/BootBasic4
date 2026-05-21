package com.basic.bootbasic4.dto;

import com.basic.bootbasic4.entity.Question;
import com.basic.bootbasic4.entity.QuestionCategory;
import com.basic.bootbasic4.entity.QuestionPetType;
import java.time.LocalDateTime;

public record QuestionSummaryDto(
    Long id,
    String title,
    Integer boardSeq,
    QuestionCategory category,
    QuestionPetType petType,
    int viewCount,
    LocalDateTime createdAt,
    String nickname,
    int answerCount
) {
    public static QuestionSummaryDto from(Question question) {
        return new QuestionSummaryDto(
                question.getId(),
                question.getTitle(),
                question.getBoardSeq(),
                question.getCategory(),
                question.getPetType(),
                question.getViewCount(),
                question.getCreatedAt(),
                question.getMember() != null ? question.getMember().getNickname() : null,
                question.getAnswers() != null ? question.getAnswers().size() : 0
        );
    }
}