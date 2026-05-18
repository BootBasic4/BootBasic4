package com.basic.bootbasic4.Service;

import com.basic.bootbasic4.Repository.QuestionRepository;
import com.basic.bootbasic4.dto.QuestionRequestDto;
import com.basic.bootbasic4.dto.QuestionResponseDto;
import com.basic.bootbasic4.entity.Question;
import com.basic.bootbasic4.entity.QuestionCategory;
import com.basic.bootbasic4.entity.QuestionPetType;
import com.basic.bootbasic4.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;

    // 1. 게시글 등록
    @Transactional
    public Long addQuestion(QuestionRequestDto dto, Member member) {
        // 1. 작성자 검증 (로그인 여부 체크)
        if (member == null || member.getId() == null) {
            throw new IllegalArgumentException(ErrorCode.MEMBER_NOT_LOGGED_IN.getMessage());
        }

        Question question = dto.toEntity();
        question.setMember(member);

        return questionRepository.save(question).getId();
    }

    // 2. 게시판별 질문 목록
    public Page<QuestionResponseDto> getListByCategory(String category, String petType, Pageable pageable) {
        QuestionCategory categoryEnum;

        try {
            categoryEnum = QuestionCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorCode.QUESTION_INVALID_CATEGORY.getMessage());
        }

        QuestionPetType petTypeEnum;
        try {
            petTypeEnum = QuestionPetType.valueOf(petType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorCode.INVALID_INPUT.getMessage());
        }

        Page<Question> questions;

        if (petTypeEnum == QuestionPetType.ALL) {
            questions = questionRepository.findByCategory(categoryEnum, pageable);
        } else {
            questions = questionRepository.findByCategoryAndPetType(categoryEnum, petTypeEnum, pageable);
        }

        return questions.map(QuestionResponseDto::from);
    }

    // 3. 상세 조회
    @Transactional
    public QuestionResponseDto getQuestionDetail(Long id) {
        Question question = findQuestionById(id);

        question.setViewCount(question.getViewCount() + 1);

        return QuestionResponseDto.from(question);
    }


    // 4. 게시글 수정
    @Transactional
    public void updateQuestion(Long id, QuestionRequestDto dto, Member currentMember) {

        Question question = findQuestionById(id);

        if (!question.getMember().getId().equals(currentMember.getId())) {
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
    public void deleteQuestion(Long id, Member currentMember) {
        Question question = findQuestionById(id);

        if (!question.getMember().getId().equals(currentMember.getId())) {
            throw new IllegalArgumentException(ErrorCode.QUESTION_UNAUTHORIZED.getMessage());
        }

        questionRepository.delete(question);
    }

    // 6. 통합 검색 (제목 + 내용)
    public Page<QuestionResponseDto> searchAll(String keyword, Pageable pageable) {
        return questionRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable)
                .map(QuestionResponseDto::from);
    }


    // ID로 게시글 찾기(예외 처리)
    private Question findQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.QUESTION_NOT_FOUND.getMessage()));
    }
}