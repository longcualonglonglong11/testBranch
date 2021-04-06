package com.fts.training.buz;

import com.fts.dbservice.sdk.exception.DBClusterException;
import com.fts.training.exception.InValidUserProfile;
import com.fts.training.exception.InconsistentStateFailException;
import com.fts.training.exception.UserRequestNotFoundException;
import com.inspire.lab.request.UserRequest;
import com.inspire.lab.request.UserResponse;

public interface UserProfileBuz {
    UserResponse handleRequest(UserRequest request) throws DBClusterException, InconsistentStateFailException;

    UserResponse create(UserRequest request) throws DBClusterException, InconsistentStateFailException, InValidUserProfile, UserRequestNotFoundException;

    UserResponse update(UserRequest request) throws DBClusterException, UserRequestNotFoundException, InValidUserProfile;

    UserResponse getUserProfileById(Long userId) throws DBClusterException;
}
