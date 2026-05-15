package com.basic.bootbasic4.Repository;

import com.basic.bootbasic4.entity.Question;
import com.basic.bootbasic4.entity.QuestionCategory;
import com.basic.bootbasic4.entity.QuestionPetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    // 1. 카테고리별(게시판별) 게시글 목록 조회
    Page<Question> findByCategory(QuestionCategory category, Pageable pageable);

    // 2. 필터링 보기 (게시판 + 동물 종류 체크)
    Page<Question> findByCategoryAndPetType(QuestionCategory category, QuestionPetType petType, Pageable pageable);

    // 3. 제목 또는 내용으로 검색
    Page<Question> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}