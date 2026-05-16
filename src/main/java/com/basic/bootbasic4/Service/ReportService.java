package com.basic.bootbasic4.Service;

import com.basic.bootbasic4.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.bootbasic4.entity.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;

    // 게시글 신고
    @Transactional
    public void reportQuestion(Long questionId, String username, String reason) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("질문글을 찾을 수 없습니다."));

        boolean alreadyReported =
                reportRepository.existsByReporterMemberIdAndQuestionId(
                        member.getMemberId(),
                        question.getId()
                );

        if (alreadyReported) {
            throw new RuntimeException("이미 신고한 게시글입니다.");
        }

        Report report = Report.builder()
                .reporter(member)
                .question(question)
                .reason(reason)
                .status("PENDING")
                .build();

        reportRepository.save(report);
    }

    // 답변 신고
    @Transactional
    public void reportAnswer(Long answerId, String username, String reason) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("답변을 찾을 수 없습니다."));

        boolean alreadyReported =
                reportRepository.existsByReporterMemberIdAndAnswerAnswerId(
                        member.getMemberId(),
                        answer.getAnswerId()
                );

        if (alreadyReported) {
            throw new RuntimeException("이미 신고한 답변입니다.");
        }

        Report report = Report.builder()
                .reporter(member)
                .answer(answer)
                .reason(reason)
                .status("PENDING")
                .build();

        reportRepository.save(report);
    }

    // 3. 전체 신고 목록 조회
    @Transactional(readOnly = true)
    public List<Report> getAllReports(){
        return reportRepository.findAllByOrderByCreatedAtDesc();
    }

    // 4. 신고 승인 처리
    @Transactional
    public void approveReport(Long reportId){

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("신고 내역을 찾을 수 없습니다."));

        report.setStatus("DELETED");

        // 질문 신고일 경우
        if(report.getQuestion() != null){
            Question question = report.getQuestion();

            report.setQuestion(null); // FK 연결 끊기
            questionRepository.delete(question);
        }

        // 답변 신고일 경우
        if (report.getAnswer() != null) {
            Answer answer = report.getAnswer();

            report.setAnswer(null); // FK 연결 끊기
            answerRepository.delete(answer);
        }
    }

    // 5. 신고 반려 처리
    @Transactional
    public void rejectReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("신고 내역을 찾을 수 없습니다."));

        report.setStatus("REJECTED");
    }
}
