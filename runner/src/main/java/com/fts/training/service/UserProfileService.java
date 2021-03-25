package com.fts.training.service;

import com.fts.training.entity.UserProfile;
import com.fts.training.service.impl.UserProfileServiceImpl;
import com.fts.training.service.impl.UserProfileServiceImpl2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface UserProfileService {
    List<UserProfile> findAll();
    UserProfile getUserProfileById(long userId);

    boolean create(long userId, String name, String email, String phone) throws Exception;
    boolean update(long userId, String name, String email, String phone) throws Exception;

}
