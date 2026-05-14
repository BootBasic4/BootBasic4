package com.basic.bootbasic4.Service;

import com.basic.bootbasic4.Repository.QuestionRepository;
import com.basic.bootbasic4.dto.QuestionRequestDto;
import com.basic.bootbasic4.dto.QuestionResponseDto;
import com.basic.bootbasic4.entity.Question;
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
        Question question = dto.toEntity();
        question.setMember(member); // 작성자 정보 연결
        return questionRepository.save(question).getId();
    }

    // 2. 게시판별 질문 목록
    public Page<QuestionResponseDto> getListByCategory(String category, String petType, Pageable pageable) {
        Page<Question> questions;

        if (petType == null || petType.equalsIgnoreCase("ALL")) {
            questions = questionRepository.findByCategory(category, pageable);
        } else {
            questions = questionRepository.findByCategoryAndPetType(category, petType, pageable);
        }

        return questions.map(QuestionResponseDto::new);
    }

    // 3. 상세 조회
    @Transactional
    public QuestionResponseDto getQuestionDetail(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        question.setViewCount(question.getViewCount() + 1);

        return new QuestionResponseDto(question);
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

    // 6. 통합 검색 (제목 + 내용)
    public Page<QuestionResponseDto> searchAll(String keyword, Pageable pageable) {
        return questionRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable)
                .map(QuestionResponseDto::new);
    }

}
