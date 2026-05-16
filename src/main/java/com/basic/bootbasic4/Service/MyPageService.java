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
    // 2. 내 정보 수정
    @Transactional
    public Member updateMyInfo(String username, Member updateMember){
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("회원정보를 찾을 수 없습니다."));

        // 닉네임 중복 검사
        if (updateMember.getNickname() != null) {
            Member existingNicknameMember =
                    memberRepository.findByNickname(updateMember.getNickname())
                            .orElse(null);

            // 자기 자신 제외 중복 검사
            if (existingNicknameMember != null
                    && !existingNicknameMember.getMemberId()
                    .equals(member.getMemberId())) {
                throw new IllegalArgumentException(
                        "이미 사용 중인 닉네임입니다.");
            }
            member.setNickname(updateMember.getNickname());
        }

        // 이메일 중복 검사
        if (updateMember.getEmail() != null) {
            Member existingEmailMember =
                    memberRepository.findByEmail(updateMember.getEmail())
                            .orElse(null);

            // 자기 자신 제외 중복 검사
            if (existingEmailMember != null
                    && !existingEmailMember.getMemberId()
                    .equals(member.getMemberId())) {
                throw new IllegalArgumentException(
                        "이미 사용 중인 이메일입니다.");
            }

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
    public void deleteMember(String username, String currentPassword) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("회원정보를 찾을 수 없습니다."));

        // 입력한 비밀번호와 DB 암호화 비밀번호 비교
        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        memberRepository.delete(member);
    }

}
