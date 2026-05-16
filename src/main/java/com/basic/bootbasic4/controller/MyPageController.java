package com.basic.bootbasic4.controller;

import com.basic.bootbasic4.Repository.MemberRepository;
import com.basic.bootbasic4.Service.MyPageService;
import com.basic.bootbasic4.dto.MyPageFormDto;
import com.basic.bootbasic4.entity.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final MemberRepository memberRepository;

    // 1, 1-1, 1-2, 마이페이지 메인: 내 정보 + 내 활동 개수 조회
    @GetMapping("/mypage")
    public String getMyInfo(Authentication authentication, Model model) {
        String username = authentication.getName();

        Member member = myPageService.getMyInfo(username);
        long questionCount = myPageService.getMyQuestionCount(username);
        long answerCount = myPageService.getMyAnswerCount(username);

        model.addAttribute("member", member);
        model.addAttribute("questionCount", questionCount);
        model.addAttribute("answerCount", answerCount);

        if (member.getPetStarted() != null && member.getPetType() != null) {
            int petYears = LocalDate.now().getYear() - member.getPetStarted();
            String petName;

            switch (member.getPetType()) {

                case "강아지":
                    petName = "강아지";
                    break;

                case "고양이":
                    petName = "고양이";
                    break;

                default:
                    petName = "반려동물";
            }

            model.addAttribute("petYears", petYears);

            model.addAttribute("petMessage",
                    petName + "와 " + petYears + "년째 함께하는 중");
        }
        return "member/mypage";
    }

    // 1-3. 내가 작성한 질문 목록
    @GetMapping("/mypage/questions")
    public String getMyQuestions(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("member", myPageService.getMyInfo(username));
        model.addAttribute("questions", myPageService.getMyQuestions(username));
        return "member/my-questions";
    }

    // 1-4. 내가 작성한 답변 목록
    @GetMapping("/mypage/answers")
    public String getMyAnswers(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("member", myPageService.getMyInfo(username));
        model.addAttribute("answers", myPageService.getMyAnswers(username));
        return "member/my-answers";
    }

    // 2. 닉네임/이메일 수정
    @PostMapping("/mypage/update")
    public String updateMyInfo(Authentication authentication, @ModelAttribute MyPageFormDto dto) {
        String username = authentication.getName();
        Member updateMember = Member.builder()
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .build();

        myPageService.updateMyInfo(username, updateMember);
        return "redirect:/mypage";
    }

    // 3. 비밀번호 변경
    @PostMapping("/mypage/password")
    @ResponseBody
    public ResponseEntity<String> updatePassword(
            Authentication authentication,
            @ModelAttribute MyPageFormDto dto) {

        String username = authentication.getName();

        try {
            myPageService.updatePassword(
                    username,
                    dto.getCurrentPassword(),
                    dto.getNewPassword()
            );
            return ResponseEntity.ok("success");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. 회원 탈퇴
    @PostMapping("/mypage/delete")
    public String deleteMember(Authentication authentication, HttpSession session) {
        String username = authentication.getName();
        myPageService.deleteMember(username);
        // 현재 로그인 세션 삭제
        session.invalidate();
        return "redirect:/login";
    }

}