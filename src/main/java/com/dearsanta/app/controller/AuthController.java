package com.dearsanta.app.controller;

import com.dearsanta.app.dto.SantaUserDto;
import com.dearsanta.app.service.OAuthService;
import com.dearsanta.app.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@Log4j
public class AuthController {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private UserService userService;


    @ModelAttribute
    public void commonAttributes(Model model){
        String clientId = oAuthService.getClientId();
        String redirectUri = oAuthService.getRedirectUri();

        model.addAttribute("clientId", clientId);
        model.addAttribute("redirectUri", redirectUri);
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping(value="/login")
    public String login(@RequestParam("code") String code, HttpSession session, RedirectAttributes rttr) {
        String access_Token = oAuthService.getKakaoAccessToken(code);
        HashMap<String, Object> userInfo = oAuthService.getUserInfo(access_Token);
        log.info("login Controller : " + userInfo);
        try {
            //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일을 저장
            if (userInfo.get("email") != null) {
                String email = userInfo.get("email").toString();
                SantaUserDto user = userService.getUserByEmail(email);
                //이메일이 DB에 없으면 회원가입
                if (user == null) {
                    user = SantaUserDto.builder()
                            .email(email)
                            .build();
                    userService.insertUser(user);
                    user = userService.getUserByEmail(email);
                }

                session.setAttribute("userId", user.getId() );
                session.setAttribute("access_Token", access_Token);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        rttr.addFlashAttribute("result", "loginSuccess");
        return "redirect:/";
    }

    @RequestMapping(value="/logout")
    public String logout(HttpSession session, RedirectAttributes rttr) {
        oAuthService.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        rttr.addFlashAttribute("result", "logoutSuccess");
        return "redirect:/";
    }


}
