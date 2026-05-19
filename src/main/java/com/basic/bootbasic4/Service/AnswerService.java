package com.basic.bootbasic4.Service;

import com.basic.bootbasic4.Repository.AnswerRepository;
import com.basic.bootbasic4.dto.AnswerFormDto;
import com.basic.bootbasic4.entity.*;
import com.basic.bootbasic4.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

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
    public void edit(Long answerId, AnswerFormDto dto) {

        // 답변 id 조회하고 없으면 에러 throw
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                ()->new NoSuchElementException(ErrorCode.ANSWER_NOT_FOUND.getMessage())
        );
        // 있는 경우에 수정 답변 반영
        answer.setContent(dto.getContent());
    }

    // 답변 삭제
    @Transactional
    public void delete(Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                ()->new NoSuchElementException(ErrorCode.ANSWER_NOT_FOUND.getMessage())
        );
        answerRepository.delete(answer);
    }
}
