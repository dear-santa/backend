package com.dearsanta.app.security;

import com.dearsanta.app.security.dto.LoginRequestDto;
import com.dearsanta.app.security.dto.LoginResponseDto;
import com.dearsanta.app.service.MemberService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Log4j
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private JwtResolver jwtResolver;

    // 회원 가입 혹은 재로그인
    @PostMapping("/kakao/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto loginRequestDto
    ) {
        log.info("/api/v1/kakao/login : 로그인 시도");
        LoginResponseDto loginResponseDto = authService.oAuthLogin(loginRequestDto);
        return new ResponseEntity(loginResponseDto, HttpStatus.OK);
    }

    @GetMapping("/test/save")
    public ResponseEntity<LoginResponseDto> email() {
        log.info("/test");
        LoginResponseDto loginResponseDto = authService.joinByEmail("myemail@test.com");
        jwtResolver.getMemberIdByAccessToken(loginResponseDto.getAccessToken());
        return new ResponseEntity(loginResponseDto, HttpStatus.OK);
    }

    @GetMapping("/test/member")
    public ResponseEntity<String> getMemberInfo(
    ) {
        String resolvedMemberId = (String) RequestContextHolder
                .currentRequestAttributes().getAttribute("memberId", RequestAttributes.SCOPE_REQUEST);

        log.info("/member 헤더로 memberId 꺼내기 : " + resolvedMemberId);
        return new ResponseEntity(resolvedMemberId, HttpStatus.OK);
    }
}