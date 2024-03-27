package com.project.teamttt.domain.repository.jpa;

import com.project.teamttt.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{
    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);

    Member findByEmailAndPassword(String email, String password);

    Optional<Member> findByEmail(String email);

    Member findByMemberId(Long memberId);


}
