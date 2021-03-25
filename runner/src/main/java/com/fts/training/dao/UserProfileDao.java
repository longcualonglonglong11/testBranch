package com.fts.training.dao;

import com.fts.training.entity.UserProfile;

import java.util.List;

public interface UserProfileDao {
    boolean create(UserProfile userProfile) throws Exception;

    boolean update(UserProfile userProfile) throws Exception;
    UserProfile findById(long userId);
    List<UserProfile> findAll();
}
