package com.project.teamttt.api.member.controller;

import com.project.teamttt.api.member.service.MemberService;
import com.project.teamttt.api.member.dto.MemberRequestDto;
import com.project.teamttt.api.util.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.project.teamttt.endpoint.AuthEndPoint.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "MEMBER API", description = "기본 로그인/회원가입 관련 api입니다.")
public class MemberController {
    private final MemberService memberService;

    /**
     * @param requestCreate
     * @return ResponseEntity<String>
     */
    @Operation(summary = "기본 회원가입",
            description = "wooraegi 기본 회원가입 API" +
                    "\n### * 회원가입 요청 예시" +
                    "\n {\n" +
                    "\n \"email\" : \"test123@gmail.com\",\n" +
                    "\n \"password\" : \"password1234\"\n" +
                    "\n }"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "회원가입에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping(MEMBER_SIGNUP)
    public ResponseEntity<String> saveMember(@RequestBody MemberRequestDto.RequestCreateMember requestCreate) {
        ResponseDto<String> response = memberService.createMember(requestCreate);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }

    /**
     * @param authenticationRequest
     * @return ResponseEntity<String>
     */
    @Operation(summary = "기본 로그인",
            description = "wooraegi 기본 로그인 API" +
                    "\n### * 로그인 요청 예시" +
                    "\n {\n" +
                    "\n \"email\" : \"test123@gmail.com\",\n" +
                    "\n \"password\" : \"password1234\"\n" +
                    "\n }"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "로그인에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping(MEMBER_LOGIN)
    public ResponseEntity<String> login(@RequestBody MemberRequestDto.AuthenticationRequest authenticationRequest) {
        ResponseEntity<String> result;
        String token = memberService.authenticateMember(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        if (token != null) {
            result = ResponseEntity.ok().body(token);
        } else {
            result = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INVALID EMAIL OR PASSWORD");
        }
        return result;
    }

}
