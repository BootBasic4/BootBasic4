package com.basic.bootbasic4.entity;

public enum QuestionCategory {
    QUESTION("질문게시판"),
    ADOPTION("분양게시판"),
    TIP("꿀팁게시판"),
    SHARE("나눔게시판"),
    FREE("자유게시판");

    private final String label;

    QuestionCategory(String label) { this.label = label; }

    public String getLabel() { return label; }
}
