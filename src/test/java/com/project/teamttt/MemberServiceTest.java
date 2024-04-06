package com.project.teamttt;


import com.project.teamttt.api.member.service.MemberService;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.config.JwtConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtConfig jwtConfig;

    @BeforeEach
    void setUp() {
        String authenticatedToken = memberService.authenticateMember("test1@example.com", "MyPassword123@");
        SecurityContextHolder.getContext().setAuthentication(jwtConfig.getAuthentication(authenticatedToken));
        System.out.println("로그인되었습니다.");
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
        System.out.println("로그아웃되었습니다.");
    }


    @Test
    @DisplayName("멤버 정보 변경 성공")
    void updateMember_Success() {
        // given
        Long memberId = 1L;
        String newNickname = "new_nickname";

        // when
        ResponseDto<String> updateResult = memberService.updateMember(memberId, newNickname);

        // then
        assertSuccessResponse(updateResult, "SUCCESS TO UPDATE MEMBER");
    }

    @Test
    @DisplayName("멤버 정보 변경 실패 : 유효하지 않은 닉네임 형식")
    void updateMember_Fail_InvalidNicknameFormat() {
        // given
        Long memberId = 1L;
        String newNickname = "a";

        // when
        ResponseDto<String> updateResult = memberService.updateMember(memberId, newNickname);

        // then
        assertFailResponse(updateResult, "INVALID NICKNAME FORMAT");
    }

    @Test
    @DisplayName("비밀번호 재설정 완료 성공")
    void completePasswordReset_Success() {
        // given
        String newPassword = "MyPassword1234@";
        Long memberId = 1L;

        // when
        ResponseDto<String> response = memberService.completePasswordReset(memberId, newPassword);

        // then
        assertSuccessResponse(response, "SUCCESS UPDATE PASSWORD");
    }

    @Test
    @DisplayName("비밀번호 재설정 완료 실패 : 유효하지 않은 비밀번호")
    void completePasswordReset_Fail_InvalidToken() {
        // given
        String newPassword = "MyNewPassword999!";
        Long memberId = 1L;

        // when
        ResponseDto<String> response = memberService.completePasswordReset(memberId, newPassword);

        // then
        assertFailResponse(response, "INVALID PASSWORD FORMAT");
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
