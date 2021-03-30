package com.fts.training.dao.impl;

import com.fts.dbservice.sdk.exception.DBClusterException;
import com.fts.training.dao.UserProfileDao;
import com.fts.training.entity.UserProfile;
import com.fts.training.exception.InValidUserProfile;
import com.fts.training.exception.UserRequestNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class UserProfileDaoImpl implements UserProfileDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private List<UserProfile> userProfiles;

    public List<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(List<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean create(UserProfile userProfile) throws DBClusterException, UserRequestNotFoundException, InValidUserProfile {


        String sql = "SELECT name FROM userprofile WHERE id = ?";
        if (jdbcTemplate.query(sql, new Object[]{userProfile.getId()}, (rs, rowNum) -> rs.getString("name")).size() != 0)
            return false;
        String createSql = "INSERT INTO userprofile (id, name, email, phone) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(createSql, userProfile.getId(), userProfile.getName(), userProfile.getEmail(), userProfile.getPhone());
        userProfiles.add(userProfile);


        return true;
    }

    public boolean update(UserProfile userProfile) throws DBClusterException, UserRequestNotFoundException, InValidUserProfile {
        String sql = "SELECT name FROM userprofile WHERE id = ?";
        if (jdbcTemplate.query(sql, new Object[]{userProfile.getId()}, (rs, rowNum) -> rs.getString("name")).size() == 0)
            return false;
        String updateSql = "UPDATE userprofile SET name = ?, email = ?, phone = ? where id = ?";
        jdbcTemplate.update(updateSql, userProfile.getName(), userProfile.getEmail(), userProfile.getPhone(), userProfile.getId());
        userProfiles.add(userProfile);

        return true;
    }

    @Override
    public UserProfile findById(long userId) {
        String sql = "SELECT * FROM userprofile WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userId}, (rs, rowNum) -> {
                UserProfile userProfile = new UserProfile();
                userProfile.setId(rs.getLong("id"));
                userProfile.setName(rs.getString("name"));
                userProfile.setEmail(rs.getString("email"));
                userProfile.setPhone(rs.getString("phone"));
                return userProfile;
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<UserProfile> findAll() {
        String sql = "select * from userprofile";
        List<UserProfile> userProfiles = new ArrayList<>();
        try {
            userProfiles = jdbcTemplate.query(sql, new Object[]{}, (rs, rowNum) -> {
                UserProfile userProfile = new UserProfile();
                userProfile.setId(rs.getLong("id"));
                userProfile.setName(rs.getString("name"));
                userProfile.setEmail(rs.getString("email"));
                userProfile.setPhone(rs.getString("phone"));
                return userProfile;

            });


        } catch (Exception e) {
//            LoggerFactory.getLogger(ClientRunner.class).warn(e.toString());

            e.printStackTrace();
        }

        return userProfiles;
    }


}
