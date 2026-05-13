package com.basic.bootbasic4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // 답변 작성자
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 어떤 질문의 답변인지
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}