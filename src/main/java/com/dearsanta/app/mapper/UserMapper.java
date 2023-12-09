package com.dearsanta.app.mapper;

import com.dearsanta.app.domain.SantaUser;

import java.util.List;

public interface UserMapper {
    public void insertUser(SantaUser entity);
    public SantaUser getUser(String id);
    public List<SantaUser> getUserList();
    public boolean updateUser(SantaUser entity);

    public boolean updateDeletedUser(SantaUser entity);
    public int deleteUser(String id);
    public SantaUser getUserByEmail(String email);
}
