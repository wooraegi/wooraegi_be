package com.project.teamttt;

import com.project.teamttt.api.member.dto.MemberRequestDto;
import com.project.teamttt.api.member.service.MemberService;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.config.JwtConfig;
import com.project.teamttt.domain.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTestByLogin {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtConfig jwtConfig;

    @Test
    @DisplayName("멤버 회원가입 성공")
    void signUp_Success() {
        // given
        MemberRequestDto.RequestCreateMember requestCreateMember = createMemberRequest("test2@example.com", "MyPassword123@", "test2134");

        // when
        ResponseDto<String> response = memberService.createMember(requestCreateMember);

        // then
        assertSuccessResponse(response, "SUCCESS SIGNUP");
    }

    @Test
    @DisplayName("멤버 회원가입 실패 : 중복된 이메일")
    void signUp_Fail_DuplicateEmail() {
        // given
        MemberRequestDto.RequestCreateMember duplicateMember = createMemberRequest("test@gmail.com", "MyPassword1234@", "test2");

        // when
        ResponseDto<String> response = memberService.createMember(duplicateMember);

        // then
        assertFailResponse(response, "DUPLICATED EMAIL");
    }

    @Test
    @DisplayName("멤버 회원가입 실패 : 중복된 닉네임")
    void signUp_Fail_DuplicateNickname() {
        // given
        MemberRequestDto.RequestCreateMember duplicateMember = createMemberRequest("test2@example.com", "MyPassword123@", "wooraegi69402");

        // when
        ResponseDto<String> response = memberService.createMember(duplicateMember);

        // then
        assertFailResponse(response, "DUPLICATED NICKNAME");
    }

    @Test
    @DisplayName("멤버 회원가입 실패 : 유효하지 않은 이메일 형식")
    void signUp_Fail_InvalidEmailFormat() {
        // given
        MemberRequestDto.RequestCreateMember createMember = createMemberRequest("test2@example", "MyPassword123@", "test2");

        // when
        ResponseDto<String> response = memberService.createMember(createMember);

        // then
        assertFailResponse(response, "INVALID EMAIL FORMAT");
    }

    @Test
    @DisplayName("멤버 회원가입 실패 : 유효하지 않은 비밀번호 형식")
    void signUp_Fail_InvalidPasswordFormat() {
        // given
        MemberRequestDto.RequestCreateMember createMember = createMemberRequest("test2@example.com", "aaa122", "test2");

        // when
        ResponseDto<String> response = memberService.createMember(createMember);

        // then
        assertFailResponse(response, "INVALID PASSWORD FORMAT");
    }

    @Test
    @DisplayName("멤버 회원가입 실패 : 유효하지 않은 닉네임 형식")
    void signUp_Fail_InvalidNicknameFormat() {
        // given
        MemberRequestDto.RequestCreateMember createMember = createMemberRequest("test2@example.com", "MyPassword123@", "a");

        // when
        ResponseDto<String> response = memberService.createMember(createMember);

        // then
        assertFailResponse(response, "INVALID NICKNAME FORMAT");
    }

    @Test
    @DisplayName("멤버 로그인 성공")
    void signIn_Success() {
        // given & when
        String authenticationResult = memberService.authenticateMember("test1@example.com", "MyPassword123@");

        // then
        assertNotNull(authenticationResult);
        assertTrue(jwtConfig.validateToken(authenticationResult));
    }

        @Test
    @DisplayName("비밀번호 재설정 성공")
    void resetPassword_Success() throws Exception {
        // given
        String email = "kimhe8005@naver.com";

        // when
        ResponseDto<String> response = memberService.resetPassword(email);

        // then
        assertSuccessResponse(response, "PASSWORD RESET SUCCESSFUL");
    }

    @Test
    @DisplayName("비밀번호 재설정 실패 : 유효하지 않은 이메일")
    void resetPassword_Fail_InvalidEmail() throws Exception {
        // given
        String email = "invalid@example.com";

        // when
        ResponseDto<String> response = memberService.resetPassword(email);

        // then
        assertFailResponse(response, "INVALID EMAIL");
    }

    private MemberRequestDto.RequestCreateMember createMemberRequest(String email, String password, String nickname) {
        return MemberRequestDto.RequestCreateMember.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .social("MEMBER")
                .role(Role.ROLE_USER)
                .build();
    }

    private void assertSuccessResponse(ResponseDto<String> response, String expectedMessage) {
        assertTrue(response.isSuccess());
        assertEquals(expectedMessage, response.getMessage());
        assertNull(response.getData());
    }

    private void assertFailResponse(ResponseDto<String> response, String expectedErrorMessage) {
        assertFalse(response.isSuccess());
        assertEquals(expectedErrorMessage, response.getMessage());
    }
}
