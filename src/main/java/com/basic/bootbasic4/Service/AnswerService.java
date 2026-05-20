package com.basic.bootbasic4.Service;

import com.basic.bootbasic4.Repository.AnswerRepository;
import com.basic.bootbasic4.Repository.ReportRepository;
import com.basic.bootbasic4.dto.AnswerFormDto;
import com.basic.bootbasic4.entity.*;
import com.basic.bootbasic4.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    // 신고중인 답변인지 알아보기 위해
    private final ReportRepository reportRepository;

    // 답변 등록
    @Transactional
    public void create(Question question, Member member, AnswerFormDto dto) {
        // question, answer 클래스 확인하고 수정하기
        // 우선 일단 작성함
        Answer answer = new Answer();
        answer.setContent(dto.getContent());
        answer.setQuestion(question);
        answer.setMember(member);
        answerRepository.save(answer);
    }

    // 답변 목록 조회
    @Transactional(readOnly = true)
    public List<Answer> getAnswersByQuestionId(Long questionId) {
        return answerRepository.findByQuestion_IdOrderByCreatedAtAsc(questionId);
    }

    // 답변 하나 조회(수정하는 페이지에서 필요)
    @Transactional(readOnly = true)
    public Optional<Answer> findById(Long answerId) {
        return answerRepository.findById(answerId);
    }

    // 답변 수정
    @Transactional
    public void edit(Long answerId, String username, AnswerFormDto dto) throws AccessDeniedException {

        // 답변 id 조회하고 없으면 에러 throw
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                ()->new NoSuchElementException(ErrorCode.ANSWER_NOT_FOUND.getMessage())
        );

        // 세션의 정보와 답변 DB 에 저장된 유저 이름이 다르면 에러 throw 하는 방어 코드
        if(!answer.getMember().getUsername().equals(username)){
            throw new AccessDeniedException(ErrorCode.ANSWER_UNAUTHORIZED.getMessage());
        }

        // 신고 접수 된 답변인지
        if(reportRepository.existsByAnswer_AnswerIdAndStatus(answerId, "PENDING")) {
            throw new IllegalArgumentException("신고된 답변은 수정할 수 없습니다.");
        }

        // 문제 없는 경우에만 수정 답변 반영
        answer.setContent(dto.getContent());
    }

    // 답변 삭제
    @Transactional
    public void delete(Long answerId, String username) throws AccessDeniedException {

        Answer answer = answerRepository.findById(answerId).orElseThrow(
                ()->new NoSuchElementException(ErrorCode.ANSWER_NOT_FOUND.getMessage())
        );

        // 마찬가지로 삭제 권한 확인 코드(답변자 본인인지)
        if (!answer.getMember().getUsername().equals(username)) {
            throw new AccessDeniedException(ErrorCode.ANSWER_UNAUTHORIZED.getMessage());
        }

        // 신고 접수 된 답변인지
        if(reportRepository.existsByAnswer_AnswerIdAndStatus(answerId, "PENDING")) {
            throw new IllegalArgumentException("신고된 답변은 삭제할 수 없습니다.");
        }

        // 신고 내역의 answer 참조 끊기
        List<Report> reports = reportRepository.findByAnswer_AnswerId(answerId);
        for (Report report : reports) {
            report.setAnswer(null);
        }

        answerRepository.delete(answer);
    }
}
