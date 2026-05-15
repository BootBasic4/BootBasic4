package com.basic.bootbasic4.Repository;

import com.basic.bootbasic4.entity.Answer;
import com.basic.bootbasic4.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    // 내가 작성한 답변 개수
    long countByMember(Member member);

    // 내가 작성한 답변 목록
    List<Answer> findByMemberOrderByCreatedAtDesc(Member member);
}
