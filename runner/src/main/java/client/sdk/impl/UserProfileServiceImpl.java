package client.sdk.impl;

import client.dao.impl.UserProfileDaoImpl;
import client.sdk.UserProfileService;
import entity.UserProfile;

import java.util.List;
import client.dao.UserProfileDao;
public class UserProfileServiceImpl implements UserProfileService {
    UserProfileDao userProfileDao = new UserProfileDaoImpl();
    @Override
    public List<UserProfile> findAll() {
        return userProfileDao.findAll();
    }

    @Override
    public UserProfile getUserProfileById(long userId) {
        return userProfileDao.findById(userId);
    }

    @Override
    public boolean create(long userId, String name, String email, String phone) {
        return userProfileDao.create(new UserProfile(userId, name, email, phone));
    }

    @Override
    public boolean update(long userId, String name, String email, String phone) {
        return userProfileDao.update(new UserProfile(userId, name, email, phone));
    }


}
