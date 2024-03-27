package com.project.teamttt.api.member.service;

import com.project.teamttt.api.member.dto.MemberRequestDto;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.config.JwtConfig;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDomainService memberDomainService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtConfig jwtConfig;


    @Transactional
    public ResponseDto<String>  createMember(MemberRequestDto.RequestCreate requestCreate) {
        if (memberDomainService.existsByEmail(requestCreate.getEmail())) {
            return new ResponseDto<>(false, "DUPLICATED EMAIL", null);
        }
        if (memberDomainService.existsByNickname(requestCreate.getNickname())) {
            return new ResponseDto<>(false, "DUPLICATED NICKNAME", null);
        }
        requestCreate.setPassword(bCryptPasswordEncoder.encode(requestCreate.getPassword()));
        memberDomainService.save(requestCreate);
        return new ResponseDto<>(true, "SUCCESS SIGNUP", null);
    }

    public String authenticateMember(String email, String password) {
        Optional<Member> optionalMember = memberDomainService.findByEmail(email);
        Member member = optionalMember.orElse(null);

        if (bCryptPasswordEncoder.matches(password, member.getPassword())) {

            return jwtConfig.generateToken(member, Duration.ofMinutes(30));
        } else {
            return null;
        }
    }
}
