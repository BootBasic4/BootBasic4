package com.basic.bootbasic4.Service;

import com.basic.bootbasic4.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.bootbasic4.entity.*;

import java.util.List;
import java.util.NoSuchElementException;

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
                .orElseThrow(() -> new NoSuchElementException("회원 정보를 찾을 수 없습니다."));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchElementException("질문글을 찾을 수 없습니다."));

        boolean alreadyReported =
                reportRepository.existsByReporterMemberIdAndQuestionId(
                        member.getMemberId(),
                        question.getId()
                );

        if (alreadyReported) {
            throw new IllegalArgumentException("이미 신고한 게시글입니다.");
        }

        Report report = Report.builder()
                .reporter(member)
                .question(question)
                .reason(reason)
                .build();

        reportRepository.save(report);
    }

    // 답변 신고
    @Transactional
    public void reportAnswer(Long answerId, String username, String reason) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("회원 정보를 찾을 수 없습니다."));

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new NoSuchElementException("답변을 찾을 수 없습니다."));

        boolean alreadyReported =
                reportRepository.existsByReporterMemberIdAndAnswerAnswerId(
                        member.getMemberId(),
                        answer.getAnswerId()
                );

        if (alreadyReported) {
            throw new IllegalArgumentException("이미 신고한 답변입니다.");
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
                .orElseThrow(() -> new NoSuchElementException("신고 내역을 찾을 수 없습니다."));

        report.setStatus("DELETED");

        // 질문 신고일 경우
        if (report.getQuestion() != null) {
            Question question = report.getQuestion();

            // 이 게시글에 달린 댓글들 먼저 처리
            List<Answer> answers = answerRepository.findByQuestion_Id(question.getId());

            for (Answer answer : answers) {
                List<Report> answerReports =
                        reportRepository.findByAnswer_AnswerId(answer.getAnswerId());

                for (Report ar : answerReports) {
                    ar.setAnswer(null);
                    ar.setStatus("DELETED");
                }

                answerRepository.delete(answer);
            }

            // 이 게시글을 참조하는 신고 내역 연결 끊기
            List<Report> questionReports =
                    reportRepository.findByQuestion_Id(question.getId());

            for (Report qr : questionReports) {
                qr.setQuestion(null);
                qr.setStatus("DELETED");
            }

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
                .orElseThrow(() -> new NoSuchElementException("신고 내역을 찾을 수 없습니다."));

        report.setStatus("REJECTED");
    }
}
