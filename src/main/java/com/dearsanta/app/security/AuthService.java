package com.dearsanta.app.security;

import com.dearsanta.app.domain.Member;
import com.dearsanta.app.domain.enumtype.Nickname;
import com.dearsanta.app.domain.enumtype.Role;
import com.dearsanta.app.mapper.MemberMapper;
import com.dearsanta.app.security.dto.LoginRequestDto;
import com.dearsanta.app.security.dto.LoginResponseDto;
import com.dearsanta.app.security.dto.OAuthType;
import com.dearsanta.app.security.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Log4j
@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final KakaoOauthClient oAuthClient;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final MemberMapper memberMapper;
    private long accessTokenValidity = Long.MAX_VALUE;
    private String bearerHeader = "Bearer ";

    public LoginResponseDto oAuthLogin(LoginRequestDto loginRequestDto) {
        String email = oAuthClient.getEmail(loginRequestDto);
        log.info("이메일 정보 얻어오기 성공 oAuthLogin : " + email);

        Member findMember = memberMapper.findMemberByEmail(email);
        if (findMember != null) {
            log.info("DB에 저장된 회원이라면 AT,RT만 반송");
            return getUpdatedToken(findMember);
        }
        log.info("첫 회원이라면 가입하기");
        return joinByEmail(email);
    }

    private LoginResponseDto getUpdatedToken(Member member) {
        String newAccessToken = updateAccessToken(member);
        String refreshToken = member.getRefreshToken();

        return LoginResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .isFirstLogin(false)
                .build();
    }

    public LoginResponseDto joinByEmail(String email) {
        String memberId = UUID.randomUUID().toString();
        TokenDto tokenDto = jwtTokenProvider.createAccessAndRefreshTokenDto(memberId);
        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();

        Member member = Member.builder()
                .id(memberId)
                .email(email)
                .nickname(Nickname.getRandomNickname())
                .refreshToken(refreshToken)
                .platform(OAuthType.KAKAO.toString())
                .role(Role.ROLE_USER.toString())
                .build();
        log.info("첫 로그인 - 회원 가입" + memberId);

        memberMapper.saveMember(member);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isFirstLogin(true)
                .build();
    }

    private String updateAccessToken(Member member) {
        String memberId = member.getId();
        String refreshToken = member.getRefreshToken();
        String newAccessToken = jwtTokenProvider.createBearerTokenWithValidity(memberId, accessTokenValidity);

        validateRefreshToken(memberId, refreshToken);
        return newAccessToken;
    }

    private void validateRefreshToken(String memberId, String refreshToken) {
        Member findMember = memberMapper.findMemberByRefreshToken(refreshToken);
        if (findMember == null) {
            throw new NoSuchElementException("해당 회원이 존재하지 않습니다.");
        }

        boolean isRefreshTokenValid = findMember.getId().equals(memberId);
        if (isRefreshTokenValid) {
            throw new NoSuchElementException("해당 회원의 리프레시 토큰이 유효하지 않습니다.");
        }
    }
}