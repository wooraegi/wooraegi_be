package com.project.teamttt.api.member.controller;

import com.project.teamttt.api.member.service.MemberService;
import com.project.teamttt.api.member.dto.MemberRequestDto;
import com.project.teamttt.api.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.project.teamttt.endpoint.AuthEndPoint.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     *
     * @param requestCreate
     * @return ResponseEntity<String>
     */
    @PostMapping(MEMBER_SIGNUP)
    public ResponseEntity<String> saveMember(@RequestBody MemberRequestDto.RequestCreate requestCreate) {
        ResponseDto<String> response = memberService.createMember(requestCreate);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }

    /**
     *
     * @param authenticationRequest
     * @return ResponseEntity<String>
     */
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
