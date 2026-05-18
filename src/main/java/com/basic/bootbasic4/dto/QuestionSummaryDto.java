package com.basic.bootbasic4.dto;

import com.basic.bootbasic4.entity.Question;
import com.basic.bootbasic4.entity.QuestionCategory;
import com.basic.bootbasic4.entity.QuestionPetType;
import java.time.LocalDateTime;

public record QuestionSummaryDto(
    Long id,
    String title,
    String authorNickname,
    QuestionCategory category,
    QuestionPetType petType,
    int viewCount,
    LocalDateTime createdAt
) {
    public static QuestionSummaryDto from(Question question) {
        return new QuestionSummaryDto(
            question.getId(),
            question.getTitle(),
            question.getMember().getNickname(),
            question.getCategory(),
            question.getPetType(),
            question.getViewCount(),
            question.getCreatedAt()
        );
    }
}