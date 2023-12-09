package com.dearsanta.app.service;

import com.dearsanta.app.dto.SantaUserDto;
import com.dearsanta.app.dto.UserListDto;

public interface UserService {
    public void insertUser(SantaUserDto dto);
    public SantaUserDto getUser(String id);
    public UserListDto getUserList();
    public boolean updateUser(SantaUserDto dto);
    public boolean updateDeletedUser(SantaUserDto dto);
    public int deleteUser(String id);
    public SantaUserDto getUserByEmail(String email);
}
