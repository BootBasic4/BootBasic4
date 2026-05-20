package com.basic.bootbasic4.Repository;

import com.basic.bootbasic4.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    // 전체 신고 목록
    List<Report> findAllByOrderByCreatedAtDesc();


    // 질문 중복 신고 체크
    boolean existsByReporterMemberIdAndQuestionId(
            Long reporterId,
            Long questionId
    );

    // 답변 중복 신고 체크
    boolean existsByReporterMemberIdAndAnswerAnswerId(
            Long reporterId,
            Long answerId
    );

    // 특정 게시글이 신고 처리중(PENDING)인지 확인
    boolean existsByQuestion_IdAndStatus(Long questionId, String status);

    // 특정 댓글이 신고 처리중(PENDING)인지 확인
    boolean existsByAnswer_AnswerIdAndStatus(Long answerId, String status);

    // 특정 댓글을 신고한 전체 신고 내역 조회
    List<Report> findByAnswer_AnswerId(Long answerId);

    // 특정 게시글을 신고한 전체 신고 내역 조회
    List<Report> findByQuestion_Id(Long questionId);
}
