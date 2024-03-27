package com.project.teamttt.domain.service;

import com.project.teamttt.api.member.dto.MemberRequestDto;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.repository.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDomainService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member save(MemberRequestDto.RequestCreate requestCreate) {
        return memberRepository.save(
                Member.builder()
                        .email(requestCreate.getEmail())
                        .password(requestCreate.getPassword())
                        .nickname(requestCreate.getNickname())
                        .social(requestCreate.getSocial())
                        .role(requestCreate.getRole())
                        .build()
        );
    }
    public Boolean existsByEmail(String email){
        if(memberRepository.existsByEmail(email)){
            return true;
        }
        return false;
    }
    public Boolean existsByNickname(String nickname){
        if(memberRepository.existsByNickname(nickname)){
            return true;
        }
        return false;
    }
    public Member findByEmailAndPassword(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member findByMemberId(Long memberId) {
        return memberRepository.findByMemberId(memberId);
    }

    @Override
    public Member loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByEmail(username);
        Member member = memberOptional.orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 가진 회원이 존재하지 않습니다."));

        return member;
    }


}
