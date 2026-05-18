package com.basic.bootbasic4.controller;

import com.basic.bootbasic4.Service.MyPageService;
import com.basic.bootbasic4.dto.MyPageFormDto;
import com.basic.bootbasic4.entity.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    // 1, 1-1, 1-2, 마이페이지 메인: 내 정보 + 내 활동 개수 조회
    @GetMapping
    public String getMyInfo(Authentication authentication, Model model) {
        String username = authentication.getName();

        Member member = myPageService.getMyInfo(username);
        long questionCount = myPageService.getMyQuestionCount(username);
        long answerCount = myPageService.getMyAnswerCount(username);

        model.addAttribute("member", member);
        model.addAttribute("questionCount", questionCount);
        model.addAttribute("answerCount", answerCount);
        model.addAttribute("petMessage", myPageService.getPetMessage(member));

        return "member/mypage";
    }

    // 1-3. 내가 작성한 질문 목록
    @GetMapping("/questions")
    public String getMyQuestions(Authentication authentication, Model model) {
        String username = authentication.getName();
        Member member = myPageService.getMyInfo(username);

        model.addAttribute("member", member);
        model.addAttribute("questions", myPageService.getMyQuestions(member));

        return "member/my-questions";
    }

    // 1-4. 내가 작성한 답변 목록
    @GetMapping("/answers")
    public String getMyAnswers(Authentication authentication, Model model) {
        String username = authentication.getName();
        Member member = myPageService.getMyInfo(username);

        model.addAttribute("member", member);
        model.addAttribute("answers", myPageService.getMyAnswers(member));

        return "member/my-answers";
    }

    // 2. 닉네임/이메일 수정
    @PostMapping("/update")
    public String updateMyInfo(Authentication authentication, @ModelAttribute MyPageFormDto dto) {
        String username = authentication.getName();
        myPageService.updateMyInfo(username, dto);

        return "redirect:/mypage";
    }

    // 3. 비밀번호 변경
    @PostMapping("/password")
    public String updatePassword(
            Authentication authentication,
            @ModelAttribute MyPageFormDto dto) {

        String username = authentication.getName();

        myPageService.updatePassword(
                username,
                dto.getCurrentPassword(),
                dto.getNewPassword()
        );

        return "redirect:/mypage";
    }

    // 4. 회원 탈퇴
    @PostMapping("/delete")
    public String deleteMember(Authentication authentication,
                               @RequestParam String currentPassword,
                               HttpSession session) {
        String username = authentication.getName();

        myPageService.deleteMember(username, currentPassword);

        session.invalidate();
        SecurityContextHolder.clearContext();

        return "redirect:/";
    }
}