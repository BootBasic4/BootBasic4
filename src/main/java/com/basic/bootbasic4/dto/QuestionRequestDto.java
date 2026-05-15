package com.basic.bootbasic4.dto;

import com.basic.bootbasic4.entity.Question;
import com.basic.bootbasic4.entity.QuestionCategory;
import com.basic.bootbasic4.entity.QuestionPetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionRequestDto {

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotBlank(message = "내용은 필수입니다")
    private String content;

    @NotNull(message = "게시판명을 입력해주세요")
    private QuestionCategory category;

    private String imageUrl;

    @NotNull(message="반려동물의 종류를 골라주세요")
    private QuestionPetType petType;


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