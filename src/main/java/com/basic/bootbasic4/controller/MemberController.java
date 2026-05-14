package com.basic.bootbasic4.controller;

import com.basic.bootbasic4.Service.MemberService;
import com.basic.bootbasic4.dto.MemberFormDto;
import com.basic.bootbasic4.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String register(@ModelAttribute MemberFormDto dto) {
        Member member = Member.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .build();

        memberService.register(member);
        return "redirect:/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

}
