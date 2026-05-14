package com.basic.bootbasic4.dto;


import com.basic.bootbasic4.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class QuestionResponseDto {

    private Long id;
    private String title;
    private String content;
    private String category;
    private String petType;
    private String imageUrl;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String nickname; // 작성자 이름


    public QuestionResponseDto(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.category = question.getCategory();
        this.petType = question.getPetType();
        this.imageUrl = question.getImageUrl();
        this.viewCount = question.getViewCount();
        this.createdAt = question.getCreatedAt();
        this.updatedAt = question.getUpdatedAt();


        if (question.getMember() != null) {
            this.nickname = question.getMember().getNickname();
        }
    }
}
