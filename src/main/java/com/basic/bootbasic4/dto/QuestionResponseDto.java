package com.basic.bootbasic4.dto;


import com.basic.bootbasic4.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponseDto {

    private Long id;
    private String title;
    private String content;
    private String category;
    private String categoryLabel;
    private String petType;
    private String imageUrl;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String nickname;
    private String username;
    private Integer commentCount;

    public static QuestionResponseDto from(Question question) {
        return QuestionResponseDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .category(question.getCategory().name())
                .categoryLabel(question.getCategory().getLabel())
                .petType(question.getPetType().name())
                .imageUrl(question.getImageUrl())
                .viewCount(question.getViewCount())
                .createdAt(question.getCreatedAt())
                .nickname(question.getMember().getNickname())
                .username(question.getMember().getUsername())
                .commentCount(question.getAnswers() != null ? question.getAnswers().size() : 0)
                .build();
    }
}
