package client.sdk;

import entity.UserProfile;

import java.util.List;

public interface UserProfileService {
    List<UserProfile> findAll();
    UserProfile getUserProfileById(long userId);

    boolean create(long userId, String name, String email, String phone);
    boolean update(long userId, String name, String email, String phone);

}
