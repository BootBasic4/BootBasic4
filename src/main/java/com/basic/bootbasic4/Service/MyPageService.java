package com.basic.bootbasic4.Service;

import com.basic.bootbasic4.Repository.*;
import com.basic.bootbasic4.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final PasswordEncoder passwordEncoder;

    // 1. 내 정보 조회
    @Transactional(readOnly = true)
    public Member getMyInfo(String username){
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("회원정보를 찾을 수 없습니다."));
    }

    // 1-1. 내가 작성한 질문 개수
    @Transactional(readOnly = true)
    public long getMyQuestionCount(String username) {
        Member member = getMyInfo(username);
        return questionRepository.countByMember(member);
    }

    // 1-2. 내가 작성한 답변 개수
    @Transactional(readOnly = true)
    public long getMyAnswerCount(String username) {
        Member member = getMyInfo(username);
        return answerRepository.countByMember(member);
    }

    // 1-3. 내가 작성한 질문 목록
    @Transactional(readOnly = true)
    public List<Question> getMyQuestions(String username) {
        Member member = getMyInfo(username);
        return questionRepository.findByMemberOrderByCreatedAtDesc(member);
    }

    // 1-4. 내가 작성한 답변 목록
    @Transactional(readOnly = true)
    public List<Answer> getMyAnswers(String username) {
        Member member = getMyInfo(username);
        return answerRepository.findByMemberOrderByCreatedAtDesc(member);
    }

    // 2. 내 정보 수정
    @Transactional
    public Member updateMyInfo(String username, Member updateMember){
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("회원정보를 찾을 수 없습니다."));

        if (updateMember.getNickname() != null) {
            member.setNickname(updateMember.getNickname());
        }

        if (updateMember.getEmail() != null) {
            member.setEmail(updateMember.getEmail());
        }

        return member;
    }

    // 3. 비밀번호 변경
    @Transactional
    public void updatePassword(String username, String currentPassword, String newPassword){
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("회원정보를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(currentPassword, member.getPassword())){
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        member.setPassword(passwordEncoder.encode(newPassword));
    }

    // 4. 회원탈퇴
    @Transactional
    public void deleteMember(String username){
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("회원정보를 찾을 수 없습니다."));

        memberRepository.delete(member);
    }

}
