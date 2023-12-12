package com.dearsanta.app.auth;

import com.dearsanta.app.domain.SantaUser;
import com.dearsanta.app.domain.enumtype.Nickname;
import com.dearsanta.app.dto.SantaUserDto;
import com.dearsanta.app.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

@Log4j
@RequiredArgsConstructor
@Component
public class OAuthService {

    private final UserMapper userMapper;
    private final RequestOauthInfoService requestOauthInfoService;
    private final JwtProvider jwtProvider;

    // 받아온 유저정보로 로그인 시도
    public String getMemberByOauthLogin(OauthParams oauthParams) {
        log.info("------ Oauth 로그인 시도 ------");

        // 인증 파라미터 객체를 이용하여 해당 enum클래스에 해당하는 메소드 수행
        OauthMember oauthMember = requestOauthInfoService.request(oauthParams);
        log.info("전달받은 유저정보:: " + oauthMember.getEmail());

        // 획득한 회원정보로 검증할 MemberDTO 생성

        SantaUserDto santaUserDto = SantaUserDto.builder()
                        .email(oauthMember.getEmail())
                        .build();

        // 획득된 회원정보 DB 조회
        SantaUser accessUser = userMapper.getUserByEmail(oauthMember.getEmail());

        // 반환할 JWT
        String accessJwt;

        if (accessUser == null) {
            accessUser = santaUserDto.toEntity();
            log.info("------ 회원가입 필요한 회원 ------");
            // 회원가입이 되지 않은 회원이기 때문에 회원 DTO에 값을 전달하여 DB저장
            log.info("회원가입 요청 :: " + accessUser.getEmail());

            // kakaoMember에서 전달된 데이터를 가진 memberDTO DB 저장
            userMapper.insertUser(accessUser);

            log.info("회원가입 완료 :: " + accessUser.getEmail());
        } else {
            if(accessUser.getIsDeleted() == 1) {
                log.info("------ 탈퇴한 회원 ------");
                // 탈퇴한 회원이기 때문에 회원 DTO에 값을 전달하여 DB저장
                log.info("재가입 요청 :: " + accessUser.getEmail());
                SantaUser backUser = SantaUser.builder()
                                .id(accessUser.getId())
                                .nickname(Nickname.getDeletedUserNickname())
                                        .build();

                System.out.println(backUser.getNickname());
                userMapper.updateDeletedUser(backUser);

                log.info("재가입 완료 :: " + accessUser.getEmail());
            }
        }
        // 이미 가입된 회원은 토큰발급
        log.info("------ JWT 발급 ------");

        accessJwt = jwtProvider.createToken(accessUser.getId());

        log.info("------ JWT 발급완료 ------");
        return accessJwt;
    }


}