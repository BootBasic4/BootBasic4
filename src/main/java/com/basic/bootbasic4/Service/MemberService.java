package com.basic.bootbasic4.Service;

import com.basic.bootbasic4.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.bootbasic4.entity.Member;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public Member register(Member member){
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRole("ROLE_USER");
        return memberRepository.save(member);
    }

    // 로그인 인증
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("회원 없음"));

        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(member.getRole().replace("ROLE_", ""))
                .build();
    }

    // 중복확인 - 아이디(로그인용)
    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    // 중복확인 - 닉네임
    public boolean existsByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    // 중복확인 - 이메일
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

}
