package com.basic.bootbasic4.Service;

import com.basic.bootbasic4.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.bootbasic4.entity.Member;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member register(Member member){
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRole("USER");
        return memberRepository.save(member);
    }

}
