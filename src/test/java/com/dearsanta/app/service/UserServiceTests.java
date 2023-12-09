package com.dearsanta.app.service;

import com.dearsanta.app.dto.SantaUserDto;
import lombok.extern.log4j.Log4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration("file:**/*-context.xml")
@Log4j
public class UserServiceTests {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService usersService;


    @Ignore
    @Test
    public void testInsertUser() {
        SantaUserDto dto = SantaUserDto.builder()
                .email("sdsad21@naver.com")
                .build();
        log.info("insertUser...");
        usersService.insertUser(dto);
    }

    @Ignore
    @Test
    public void testGetUser() {
        log.info("getUser...");
        log.info(usersService.getUser("b656d400-02fa-49ef-9aa0-681aa73b4ba8"));
    }

    @Ignore
    @Test
    public void testGetUserByEmail() {
        log.info("getUserByEmail...");
        log.info(usersService.getUserByEmail("brotherjun96@kakao.com"));
    }

    @Ignore
    @Test
    public void testGetUserList() {
        log.info("getUserList...");
        log.info(usersService.getUserList().toString());
    }

    @Ignore
    @Test
    public void testUpdateUser() {
        SantaUserDto dto = SantaUserDto.builder()
                .id("b656d400-02fa-49ef-9aa0-681aa73b4ba8")
                .nickname("test")
                .imgUrl("test.com")
                .build();
        log.info("updateUser...");
        log.info(usersService.updateUser(dto));
    }

    //@Ignore
    @Test
    public void testUpdateDeletedUser() {
        SantaUserDto dto = SantaUserDto.builder()
                .id("18cd435c-e931-4cfc-b70e-89b2153f091a")
                .build();
        log.info("updateDeletedUser...");
        log.info(usersService.updateDeletedUser(dto));
    }

    @Ignore
    @Test
    public void testDeleteUser() {
        int result = usersService.deleteUser("b656d400-02fa-49ef-9aa0-681aa73b4ba8");

        log.info("deleteUser..." + result);

    }
}
