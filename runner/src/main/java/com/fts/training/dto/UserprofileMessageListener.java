package com.fts.training.dto;

import com.fts.dbservice.sdk.exception.DBClusterException;
import com.fts.icb.common.cache.CacheHelper;
import com.fts.training.buz.UserProfileBuz;
import com.inspire.lab.config.Listener;
import com.inspire.lab.request.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserprofileMessageListener extends Listener {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserprofileMessageListener.class);
    @Autowired
    UserProfileBuz userProfileBuz;
    @Autowired
    CacheHelper cacheHelper;

    @Override
    public void response(UserRequest data) throws Exception {
        super.response(data);
        LOGGER.info("Start createUserProfileListener userId: {}", data.getUserID());
        try {
//            if (data.getState().equals(State.NEW))
            userProfileBuz.create(data);

        } catch (DBClusterException e) {
            throw e;
        }

        LOGGER.info("End createUserProfileListener userId: {}", data.getUserID());

    }
}
