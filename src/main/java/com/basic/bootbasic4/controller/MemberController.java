package com.basic.bootbasic4.controller;

import com.basic.bootbasic4.Service.MemberService;
import com.basic.bootbasic4.dto.MemberFormDto;
import com.basic.bootbasic4.entity.Member;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/signup";
    }

    // 중복확인 - 아이디
    @GetMapping("/check-username")
    @ResponseBody
    public String checkUsername(@RequestParam String username) {
        return memberService.existsByUsername(username) ? "duplicate" : "available";
    }

    // 중복확인 - 닉네임
    @GetMapping("/check-nickname")
    @ResponseBody
    public String checkNickname(@RequestParam String nickname) {
        return memberService.existsByNickname(nickname) ? "duplicate" : "available";
    }

    // 중복확인 - 이메일
    @GetMapping("/check-email")
    @ResponseBody
    public String checkEmail(@RequestParam String email) {
        return memberService.existsByEmail(email) ? "duplicate" : "available";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String register(@Valid @ModelAttribute MemberFormDto dto,
                           BindingResult bindingResult) {

        // DTO 기본 검증 실패
        if (bindingResult.hasErrors()) {
            return "redirect:/signup?validationError=true";
        }

        // 서버단 아이디 중복 검사
        if (memberService.existsByUsername(dto.getUsername())) {
            return "redirect:/signup?usernameDuplicate=true";
        }

        // 서버단 닉네임 중복 검사
        if (memberService.existsByNickname(dto.getNickname())) {
            return "redirect:/signup?nicknameDuplicate=true";
        }

        // 서버단 이메일 중복 검사
        if (memberService.existsByEmail(dto.getEmail())) {
            return "redirect:/signup?emailDuplicate=true";
        }

        // 비밀번호 정책 검사
        if (!isValidPassword(dto.getPassword())) {
            return "redirect:/signup?passwordRuleError=true";
        }

        // 비밀번호 확인 검사
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return "redirect:/signup?passwordError=true";
        }

        Member member = Member.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .petType(dto.getPetType())
                .petStarted(dto.getPetStarted())
                .build();

        memberService.register(member);

        return "redirect:/login";
    }

    // 비밀번호 정책 검사
    private boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        String noSpacePassword = password.replaceAll("\\s", "");

        if (noSpacePassword.length() < 8 || noSpacePassword.length() > 16) {
            return false;
        }

        if (password.matches(".*(.)\\1\\1.*")) {
            return false;
        }
        int count = 0;

        if (password.matches(".*[A-Za-z].*")) {
            count++;
        }

        if (password.matches(".*\\d.*")) {
            count++;
        }

        if (password.matches(".*[@$!%*#?&].*")) {
            count++;
        }
        return count >= 2;
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }
}