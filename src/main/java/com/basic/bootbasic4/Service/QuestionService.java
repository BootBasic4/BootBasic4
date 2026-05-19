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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Long addQuestion(QuestionRequestDto dto, Member member) {
        Question question = dto.toEntity();
        question.setMember(member);
        return questionRepository.save(question).getId();
    }

    // 2. 게시판별 질문 목록 (카테고리 + petType 필터)
    public Page<QuestionResponseDto> getListByCategory(String category, String petType, Pageable pageable) {
        QuestionCategory categoryEnum = QuestionCategory.valueOf(category.toUpperCase());

        Page<Question> questions;
        if (petType == null || petType.equalsIgnoreCase("ALL")) {
            questions = questionRepository.findByCategory(categoryEnum, pageable);
        } else {
            QuestionPetType petTypeEnum = QuestionPetType.valueOf(petType.toUpperCase());
            questions = questionRepository.findByCategoryAndPetType(categoryEnum, petTypeEnum, pageable);
        }
        return questions.map(QuestionResponseDto::from);
    }

    // 3. 상세 조회 + 조회수 증가
    @Transactional
    public QuestionResponseDto getQuestionDetail(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        questionRepository.increaseViewCount(id);
        return QuestionResponseDto.from(question);
    }

    // 4. 게시글 수정
    @Transactional
    public void updateQuestion(Long id, QuestionRequestDto dto) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        question.setTitle(dto.getTitle());
        question.setContent(dto.getContent());
        question.setCategory(dto.getCategory());
        question.setImageUrl(dto.getImageUrl());
        question.setPetType(dto.getPetType());
    }

    // 5. 게시글 삭제
    @Transactional
    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        questionRepository.delete(question);
    }

    // 6. 검색 + 필터 + 페이징 (Specification 기반)
    public Page<QuestionSummaryDto> search(String keyword, QuestionCategory category, QuestionPetType petType, Pageable pageable) {
        Specification<Question> spec = QuestionSpecification.withCondition(keyword, category, petType);
        return questionRepository.findAll(spec, pageable)
                .map(QuestionSummaryDto::from);
    }

}

