package com.dearsanta.app.controller;

import com.dearsanta.app.dto.SantaUserDto;
import com.dearsanta.app.dto.UserListDto;
import com.dearsanta.app.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@Log4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public ResponseEntity<SantaUserDto> getUser(HttpSession session) {
        String id = session.getAttribute("userId").toString();
        log.info("getUser...");
        try {
            SantaUserDto user = userService.getUser(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getList")
    public ResponseEntity<UserListDto> getUserList() {
        log.info("getUser...");
        userService.getUserList();
        try {
            UserListDto userList = userService.getUserList();
            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody SantaUserDto dto) {
        log.info("updateUser...");
        userService.updateUser(dto);
        try {
            userService.updateUser(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
