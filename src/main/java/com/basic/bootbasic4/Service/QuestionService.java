package com.basic.bootbasic4.Service;

import com.basic.bootbasic4.Repository.MemberRepository;
import com.basic.bootbasic4.Repository.QuestionRepository;
import com.basic.bootbasic4.Repository.QuestionSpecification;
import com.basic.bootbasic4.dto.QuestionRequestDto;
import com.basic.bootbasic4.dto.QuestionResponseDto;
import com.basic.bootbasic4.dto.QuestionSummaryDto;
import com.basic.bootbasic4.entity.Member;
import com.basic.bootbasic4.entity.Question;
import com.basic.bootbasic4.entity.QuestionCategory;
import com.basic.bootbasic4.entity.QuestionPetType;
import com.basic.bootbasic4.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    // 1. 게시글 등록
    @Transactional
    public Long addQuestion(QuestionRequestDto dto, String username) {

        if (username == null) {
            throw new IllegalArgumentException(ErrorCode.MEMBER_NOT_LOGGED_IN.getMessage());
        }

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Question question = dto.toEntity();
        question.setMember(member);

        return questionRepository.save(question).getId();
    }


    // 3. 상세 조회 + 조회수 증가
    @Transactional
    public QuestionResponseDto getQuestionDetail(Long id) {
        questionRepository.increaseViewCount(id);

        Question question = findQuestionById(id);

        return QuestionResponseDto.from(question);
    }


    // 4. 게시글 수정
    @Transactional
    public void updateQuestion(Long id, QuestionRequestDto dto, String currentUsername) {

        Question question = findQuestionById(id);

        if (!question.getMember().getUsername().equals(currentUsername)) {
            throw new IllegalArgumentException(ErrorCode.QUESTION_UNAUTHORIZED.getMessage());
        }

        question.setTitle(dto.getTitle());
        question.setContent(dto.getContent());
        question.setCategory(dto.getCategory());
        question.setImageUrl(dto.getImageUrl());
        question.setPetType(dto.getPetType());
    }


    // 5. 게시글 삭제
    @Transactional
    public void deleteQuestion(Long id, String currentUsername) {
        Question question = findQuestionById(id);

        if (!question.getMember().getUsername().equals(currentUsername)) {
            throw new IllegalArgumentException(ErrorCode.QUESTION_UNAUTHORIZED.getMessage());
        }

        questionRepository.delete(question);
    }

    // 6. 검색 + 필터 + 페이징 (Specification 기반)
    public Page<QuestionSummaryDto> search(String keyword, QuestionCategory category, QuestionPetType petType, Pageable pageable) {
        Specification<Question> spec = QuestionSpecification.withCondition(keyword, category, petType);
        return questionRepository.findAll(spec, pageable)
                .map(QuestionSummaryDto::from);
    }


    // ID로 게시글 찾기(예외 처리)
    private Question findQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.QUESTION_NOT_FOUND.getMessage()));
    }
}
