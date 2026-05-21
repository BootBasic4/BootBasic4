package com.basic.bootbasic4.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionPetType {
    ALL("전체"),
    DOG("강아지"),
    CAT("고양이");

    private final String label;
}
