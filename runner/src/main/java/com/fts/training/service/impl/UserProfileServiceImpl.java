package com.fts.training.service.impl;

import com.fts.training.dao.impl.UserProfileDaoImpl;
import com.fts.training.service.UserProfileService;
import com.fts.training.entity.UserProfile;

import java.util.List;
import com.fts.training.dao.UserProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
@Component
@Primary
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
    public boolean create(long userId, String name, String email, String phone) throws Exception {
        return userProfileDao.create(new UserProfile(userId, name, email, phone));
    }

    @Override
    public boolean update(long userId, String name, String email, String phone) throws Exception {
        return userProfileDao.update(new UserProfile(userId, name, email, phone));
    }


}
