package com.basic.bootbasic4.Repository;

import com.basic.bootbasic4.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    // 전체 신고 목록
    List<Report> findAllByOrderByCreatedAtDesc();

    // 미처리 신고 목록
    List<Report> findByStatusOrderByCreatedAtDesc(String status);

    // 질문 중복 신고 체크
    boolean existsByReporterMemberIdAndQuestionQuestionId(
            Long reporterId,
            Long questionId
    );

    // 답변 중복 신고 체크
    boolean existsByReporterMemberIdAndAnswerAnswerId(
            Long reporterId,
            Long answerId
    );

}
