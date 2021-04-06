package com.fts.training.service;

import com.fts.dbservice.sdk.exception.DBClusterException;
import com.fts.training.entity.UserProfile;
import com.fts.training.exception.InValidUserProfile;
import com.fts.training.exception.UserRequestNotFoundException;

import java.util.List;

public interface UserProfileService {
    List<UserProfile> findAll();

    UserProfile getUserProfileById(long userId);

    boolean create(long userId, String name, String email, String phone) throws DBClusterException, UserRequestNotFoundException, InValidUserProfile;

    boolean update(long userId, String name, String email, String phone) throws DBClusterException, UserRequestNotFoundException, InValidUserProfile;

}
