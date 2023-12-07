package com.dearsanta.app.service;

import com.dearsanta.app.domain.SantaUser;
import com.dearsanta.app.dto.SantaUserDto;
import com.dearsanta.app.dto.UserListDto;
import com.dearsanta.app.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public void insertUser(SantaUserDto dto) {
        SantaUser user =  dto.toEntity();
        log.info("insertUser...");
        mapper.insertUser(user);
    }

    @Override
    public SantaUserDto getUser(String id) {
        log.info("getUser...");
        SantaUser user = mapper.getUser(id);
        return SantaUser.toDto(user);
    }
    @Override
    public SantaUserDto getUserByEmail(String email) {
        log.info("getUserByEmail...");
        SantaUser user = mapper.getUserByEmail(email);
        if (user == null) {
            return null;
        }
        return SantaUser.toDto(user);
    }
    @Override
    public UserListDto getUserList() {
        log.info("getUserList...");
        List<SantaUser> userList = mapper.getUserList();
        List<SantaUserDto> userDtoList = new ArrayList<>();

        for (SantaUser user : userList) {
            SantaUserDto userDto = SantaUser.toDto(user);
            userDtoList.add(userDto);
        }

        UserListDto userListDto = UserListDto.builder()
                .userList(userDtoList)
                .build();
        return userListDto;
    }

    @Override
    public boolean updateUser(SantaUserDto dto) {
        SantaUser user =  dto.updateToEntity();
        log.info("updateUser...");
        return mapper.updateUser(user);
    }

    @Override
    public int deleteUser(String id) {
        int result = mapper.deleteUser(id);
        if(result == 1){
            log.info("deleteUser..." + id);
        }else{
            log.info("deleteUser fail..." + id);
        }
        return result;
    }


}
