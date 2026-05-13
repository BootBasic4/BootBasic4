package com.basic.bootbasic4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(nullable = false, length = 200)
    private String reason;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // 신고한 회원
    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private Member reporter;

    // 신고 대상 질문
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    // 신고 대상 답변
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;
}

