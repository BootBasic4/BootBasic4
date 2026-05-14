package com.basic.bootbasic4.dto;

import com.basic.bootbasic4.entity.Question;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionRequestDto {

    private String title;
    private String content;
    private String category;
    private String imageUrl;
    private String petType;


    public Question toEntity() {
        return Question.builder()
                .title(this.title)
                .content(this.content)
                .category(this.category)
                .petType(this.petType)
                .imageUrl(this.imageUrl)
                .viewCount(0)
                .build();
    }
}