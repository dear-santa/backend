package com.dearsanta.app.mapper;

import com.dearsanta.app.domain.SantaUser;
import com.dearsanta.app.domain.enumtype.Nickname;
import com.dearsanta.app.domain.enumtype.Platform;
import com.dearsanta.app.domain.enumtype.Role;
import lombok.extern.log4j.Log4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.UUID;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration("file:**/*-context.xml")
@Log4j
public class UserMapperTests {

    @Autowired
    private UserMapper mapper;

    @Ignore
    @Test
    public void insertUser(){
        SantaUser entity = SantaUser.builder()
                        .id(UUID.randomUUID().toString())
                        .email("aaa22@gmail.com")
                        .nickname(Nickname.getRandomNickname().toString())
                        .role(Role.ROLE_USER.toString())
                        .platform(Platform.KAKAO.toString())
                        .build();
        log.info("insertUser...");
        mapper.insertUser(entity);

    }

    @Ignore
    @Test
    public void getUser() {
        SantaUser entity = mapper.getUser("b656d400-02fa-49ef-9aa0-681aa73b4ba8");
        log.info("getUser : " + entity.toString());

        System.out.println(entity.toString());
    }

    @Ignore
    @Test
    public void getUserList() {
        List<SantaUser> list = mapper.getUserList();
        log.info("getUserList : " + list.toString());
        System.out.println(list.toString());
    }

    @Ignore
    @Test
    public void updateUser() {
        SantaUser entity = SantaUser.builder()
                .id("b656d400-02fa-49ef-9aa0-681aa73b4ba8")
                .nickname("안녕")
                .imgUrl("aaa.com")
                .build();
        log.info("updateUser : " + mapper.updateUser(entity));
    }

    //@Ignore
    @Test
    public void updateDeletedUser() {
        SantaUser entity = SantaUser.builder()
                .id("18cd435c-e931-4cfc-b70e-89b2153f091a")
                .nickname(Nickname.getDeletedUserNickname().toString())
                .build();
        log.info("updateDeletedUser : " + mapper.updateDeletedUser(entity));
    }

    @Ignore
    @Test
    public void deleteUser() {
        log.info("deleteUser : " + mapper.deleteUser("b656d400-02fa-49ef-9aa0-681aa73b4ba8"));
    }

    @Ignore
    @Test
    public void getUserByEmail() {
        log.info("getUserByEmail : " + mapper.getUserByEmail("aaa4@gmail.com"));
    }
}
