package com.basic.bootbasic4.controller;

import com.basic.bootbasic4.Service.MyPageService;
import com.basic.bootbasic4.dto.MyPageFormDto;
import com.basic.bootbasic4.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    // 1. 내 정보 조회
    @GetMapping("/mypage")
    public String getMyInfo(Authentication authentication, Model model){
        String username = authentication.getName();
        Member member = myPageService.getMyInfo(username);
        model.addAttribute("member", member);
        return "mypage";
    }

    // 2. 닉네임 수정
    @PostMapping("/mypage/update")
    public String updateMyInfo(Authentication authentication, @ModelAttribute MyPageFormDto dto){
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
    public String updatePassword(Authentication authentication, @ModelAttribute MyPageFormDto dto){
        String username = authentication.getName();
        myPageService.updatePassword(username, dto.getCurrentPassword(), dto.getNewPassword());
        return "redirect:/mypage";
    }

    // 4. 회원 탈퇴
    @PostMapping("/mypage/delete")
    public String deleteMember(Authentication authentication, HttpSession session){
        String username = authentication.getName();
        myPageService.deleteMember(username);
        session.invalidate();
        return "redirect:/logout";
    }

}
