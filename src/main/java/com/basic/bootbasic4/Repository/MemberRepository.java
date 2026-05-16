package com.basic.bootbasic4.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.basic.bootbasic4.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEmail(String email);

}
