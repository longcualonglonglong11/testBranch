package com.fts.training.service.impl;

import com.fts.dbservice.sdk.exception.DBClusterException;
import com.fts.training.dao.UserProfileDao;
import com.fts.training.entity.UserProfile;
import com.fts.training.exception.InValidUserProfile;
import com.fts.training.exception.UserRequestNotFoundException;
import com.fts.training.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    UserProfileDao userProfileDao;

    @Override
    public List<UserProfile> findAll() {
        return userProfileDao.findAll();
    }

    @Override
    public UserProfile getUserProfileById(long userId) {
        return userProfileDao.findById(userId);
    }

    @Override
    public boolean create(long userId, String name, String email, String phone) throws DBClusterException, UserRequestNotFoundException, InValidUserProfile {
        return userProfileDao.create(new UserProfile(userId, name, email, phone));
    }

    @Override
    public boolean update(long userId, String name, String email, String phone) throws DBClusterException, UserRequestNotFoundException, InValidUserProfile {
        return userProfileDao.update(new UserProfile(userId, name, email, phone));
    }


}
