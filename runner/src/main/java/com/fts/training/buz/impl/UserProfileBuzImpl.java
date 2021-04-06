package com.fts.training.buz.impl;

import com.fts.dbservice.sdk.exception.DBClusterException;
import com.fts.icb.common.cache.CacheHelper;
import com.fts.icb.common.genuid.GenUID;
import com.fts.training.buz.MessageProducer;
import com.fts.training.buz.UserProfileBuz;
import com.fts.training.entity.UserProfile;
import com.fts.training.enums.State;
import com.fts.training.exception.InValidUserProfile;
import com.fts.training.exception.InconsistentStateFailException;
import com.fts.training.exception.UserRequestNotFoundException;
import com.fts.training.service.UserProfileService;
import com.inspire.lab.request.UserRequest;
import com.inspire.lab.request.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserProfileBuzImpl implements UserProfileBuz {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileBuzImpl.class);
    private static final long CACHE_EXPIRED_3HOURS = 10800;
    private static final String CACHE_USER_STATE = "UserState";
    @Autowired
    GenUID genUID;
    @Autowired
    private CacheHelper cacheHelper;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private MessageProducer messageProducer;

    public UserProfileBuzImpl() {
    }

    private static String getCacheUserStateKey(Long userId) {
        return CACHE_USER_STATE + userId;
    }


    @Override
    public UserResponse handleRequest(UserRequest request) throws DBClusterException, InconsistentStateFailException {
        //generate user id
        Long userId = genUID.getUID();
        //send create user profile to kafka
        request.setUserID(userId);
        messageProducer.sendCreateUserProfile(request);
        request.setState(State.NEW);

        cacheHelper.putIntoBucket(getCacheUserStateKey(userId), new UserProfile(userId, (State) request.getState()), CACHE_EXPIRED_3HOURS);


        LOGGER.info("STATE RESPONSE: {}", request.getState());
        return new UserResponse(userId, request.getUsername(), request.getEmail(), request.getPhone(), request.getState());
    }

    @Override
    public UserResponse create(UserRequest request) throws DBClusterException, InconsistentStateFailException, InValidUserProfile, UserRequestNotFoundException {
//        Long userId = request.getUserID();
        //create user profile on database
//        boolean userProfile = userProfileService.create(userId, request.getUsername(), request.getPhone(), request.getEmail());
        //update state to cache
//        cacheHelper.putIntoBucket(getCacheUserStateKey(userId), userProfile, CACHE_EXPIRED_3HOURS);
//        userProfile.setState(State.ACTIVE);
//        LOGGER.info("STATE : {} ", userProfile.getState());
        request.setState(State.ACTIVE);

        cacheHelper.putIntoBucket(getCacheUserStateKey(request.getUserID()), request, CACHE_EXPIRED_3HOURS);
        LOGGER.info("STATE : {} ", request.getState());
        userProfileService.create(request.getUserID(), request.getUsername(), request.getEmail(), request.getPhone());
        return new UserResponse(request.getUserID(), request.getUsername(), request.getEmail(), request.getPhone());

    }

    @Override
    public UserResponse update(UserRequest request) throws DBClusterException, UserRequestNotFoundException, InValidUserProfile {
        Long userIdInput = request.getUserID();
        UserProfile userProfile = userProfileService.getUserProfileById(userIdInput);

        userProfileService.update(request.getUserID(), request.getUsername(), request.getPhone(), request.getEmail());
        return new UserResponse(request.getUserID(), request.getUsername(), request.getPhone(), request.getEmail());

    }

    @Override
    public UserResponse getUserProfileById(Long userId) throws DBClusterException {
        UserProfile userProfile = userProfileService.getUserProfileById(userId);
        return new UserResponse(userProfile.getId(), userProfile.getName(), userProfile.getEmail(), userProfile.getPhone());
    }
}
