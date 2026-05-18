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
    // save -> 답변 저장
    // findById -> 수정하고 삭제할때 특정 답변하나 가져오는거 필요
    // delete -> 답변 삭제

    // 특정 질문에 대한 답변 목록 조회
    // 생성일자 기준으로 오름차순으로
    // QuesionDto 확인하고 수정하기
    List<Answer> findByQuestion_QuestionIdOrderByCreatedAtAsc(Long questionId);
}
