package com.fts.training.dao;

import com.fts.dbservice.sdk.exception.DBClusterException;
import com.fts.training.entity.UserProfile;
import com.fts.training.exception.InValidUserProfile;
import com.fts.training.exception.UserRequestNotFoundException;

import java.util.List;

public interface UserProfileDao {
    boolean create(UserProfile userProfile) throws DBClusterException, UserRequestNotFoundException, InValidUserProfile;

    boolean update(UserProfile userProfile) throws DBClusterException, UserRequestNotFoundException, InValidUserProfile;

    UserProfile findById(long userId);

    List<UserProfile> findAll();
}
