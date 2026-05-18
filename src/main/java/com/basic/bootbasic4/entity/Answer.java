package com.basic.bootbasic4.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="answer")
@Getter
@Setter
@NoArgsConstructor
public class Answer {

    // 답변 고유 번호
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="answer_id")
    private Long answerId;

    // 답변 내용
    @Column(columnDefinition="TEXT", nullable=false)
    private String content;

    // 질문 고유 번호
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="question_id", nullable=false)
    private Question question;

    // 작성자 고유 번호
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id", nullable=false)
    private Member member;

    // 생성 일자
    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    // 수정 일자
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    // insert 직전에 실행
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // update 직전에 실행
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
