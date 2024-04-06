package com.project.teamttt.api.member.service;

import com.project.teamttt.api.member.dto.MemberRequestDto;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.api.util.ValidationUtils;
import com.project.teamttt.config.JwtConfig;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService {
    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int DEFAULT_LENGTH = 6;

    private final MemberDomainService memberDomainService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtConfig jwtConfig;
    private final ValidationUtils validationUtils;
    private final EmailService emailService;


    @Transactional
    public ResponseDto<String>  createMember(MemberRequestDto.RequestCreateMember requestCreate) {
        if(!validationUtils.isValidEmail(requestCreate.getEmail())){
            return new ResponseDto<>(false, "INVALID EMAIL FORMAT", null);
        }else if(!validationUtils.isValidNickname(requestCreate.getNickname())){
            return new ResponseDto<>(false, "INVALID NICKNAME FORMAT", null);
        }else if(!validationUtils.isValidPassword(requestCreate.getPassword())){
            return new ResponseDto<>(false, "INVALID PASSWORD FORMAT", null);
        }

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

    @Transactional
    public ResponseDto<String> updateMember(Long memberId, String nickname) {
        try {
            if(!validationUtils.isValidNickname(nickname)){
                return new ResponseDto<>(false, "INVALID NICKNAME FORMAT", null);
            }

            Member memberDetail = memberDomainService.findByMemberId(memberId);
            memberDetail.setNickname(nickname);
            memberDomainService.save(memberDetail);
            return new ResponseDto<>(true, "SUCCESS TO UPDATE MEMBER", null);
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO UPDATE MEMBER: " + e.getMessage(), null);
        }
    }

    public ResponseDto<String> resetPassword(String email) throws Exception {
        Optional<Member> optionalMember = memberDomainService.findByEmail(email);
        Member member = optionalMember.orElse(null);

        if (member == null) {
            return new ResponseDto<>(false, "INVALID EMAIL", null);
        }

        String newPassword = generateRandomPassword();

        member.setPassword(bCryptPasswordEncoder.encode(newPassword));
        memberDomainService.save(member);

        emailService.sendMail(newPassword, email);

        return new ResponseDto<>(true, "PASSWORD RESET SUCCESSFUL", null);
    }

    public static String generateRandomPassword() {
        return generateRandomPassword(DEFAULT_LENGTH);
    }

    public static String generateRandomPassword(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("INVALID PASSWORD LENGTH");
        }

        Random random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            password.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        }

        return password.toString();
    }

    public ResponseDto<String> completePasswordReset(Long memberId, String newPassword) {
        try {
            if(!validationUtils.isValidPassword(newPassword)){
                return new ResponseDto<>(false, "INVALID PASSWORD FORMAT", null);
            }

            Member member = memberDomainService.findByMemberId(memberId);
            member.setPassword(newPassword);
            memberDomainService.save(member);

            return new ResponseDto<>(true, "SUCCESS UPDATE PASSWORD", null);
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO UPDATE PASSWORD : " + e.getMessage(), null);
        }
    }
}
