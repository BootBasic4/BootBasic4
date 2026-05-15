package com.basic.bootbasic4.Repository;

import com.basic.bootbasic4.entity.Question;
import com.basic.bootbasic4.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // 내가 작성한 질문 개수
    long countByMember(Member member);

    // 내가 작성한 질문 목록
    List<Question> findByMemberOrderByCreatedAtDesc(Member member);
}
