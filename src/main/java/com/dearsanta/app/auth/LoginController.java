package com.dearsanta.app.auth;



import com.dearsanta.app.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Log4j
@Controller
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private OAuthService oauthService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/jwt/kakao")
    public ResponseEntity<String> handleKakaoLogin(@RequestBody KakaoParams kakaoParams){
        log.debug("넘겨받은 Kakao 인증키 :: " + kakaoParams.getAuthorizationCode());

        String jwttoken = oauthService.getMemberByOauthLogin(kakaoParams);
        //응답 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set("jwttoken", jwttoken);

        return ResponseEntity.ok().headers(headers).body("success");
    }


    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> unlink(String jwtToken) {
        String userId = jwtProvider.getSubject(jwtToken);

        //db 삭제
        log.info("deleteUser...");
        userService.deleteUser(userId);

        return ResponseEntity.ok().body("success");
    }


}
