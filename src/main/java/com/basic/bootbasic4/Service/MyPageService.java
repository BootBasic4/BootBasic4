package com.basic.bootbasic4.Service;

import com.basic.bootbasic4.Repository.*;
import com.basic.bootbasic4.dto.MyPageFormDto;
import com.basic.bootbasic4.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

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
                .orElseThrow(() -> new NoSuchElementException("회원정보를 찾을 수 없습니다."));
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
    public List<Question> getMyQuestions(Member member) {
        return questionRepository.findByMemberOrderByCreatedAtDesc(member);
    }

    // 1-4. 내가 작성한 답변 목록
    @Transactional(readOnly = true)
    public List<Answer> getMyAnswers(Member member) {
        return answerRepository.findByMemberOrderByCreatedAtDesc(member);
    }

    // 반려동물 동거 기간 메시지 생성
    public String getPetMessage(Member member) {

        if (member.getPetStarted() == null || member.getPetType() == null) {
            return null;
        }
        int petYears = LocalDate.now().getYear() - member.getPetStarted();
        return member.getPetType() + "와 " + petYears + "년째 함께하는 중";
    }

    // 2. 내 정보 수정
    @Transactional
    public Member updateMyInfo(String username, MyPageFormDto dto) {
        Member member = getMyInfo(username);

        // 닉네임 중복 검사
        if (dto.getNickname() != null
                && !dto.getNickname().equals(member.getNickname())
                && memberRepository.existsByNickname(dto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 이메일 중복 검사
        if (dto.getEmail() != null
                && !dto.getEmail().equals(member.getEmail())
                && memberRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (dto.getNickname() != null) {
            member.setNickname(dto.getNickname());
        }

        if (dto.getEmail() != null) {
            member.setEmail(dto.getEmail());
        }

        return member;
    }

    // 3. 비밀번호 변경
    @Transactional
    public void updatePassword(String username, String currentPassword, String newPassword){
        Member member = getMyInfo(username);

        if (!passwordEncoder.matches(currentPassword, member.getPassword())){
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        member.setPassword(passwordEncoder.encode(newPassword));
    }

    // 4. 회원탈퇴
    @Transactional
    public void deleteMember(String username, String currentPassword) {
        Member member = getMyInfo(username);

        // 입력한 비밀번호와 DB 암호화 비밀번호 비교
        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        memberRepository.delete(member);
    }

}
