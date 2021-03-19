package client.dao.impl;

import client.dao.UserProfileDao;
import entity.UserProfile;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class UserProfileDaoImpl implements UserProfileDao {
    private List<UserProfile> userProfiles;
    JdbcTemplate jdbcTemplate;

    public UserProfileDaoImpl() {
        jdbcTemplate = new JdbcTemplate();
        userProfiles = new ArrayList<>();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://0.0.0.0:5432/rainbow_database");
        dataSource.setUsername("unicorn_user");
        dataSource.setPassword("magical_password");
        jdbcTemplate.setDataSource(dataSource);
    }

    private DataSource dataSource;

    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public boolean create(UserProfile userProfile) {


        try {
            String sql = "SELECT name FROM userprofile WHERE id = ?";
            if (jdbcTemplate.query(sql, new Object[]{userProfile.getId()}, (rs, rowNum) -> rs.getString("name")).size() != 0)
                return false;
            String createSql = "INSERT INTO userprofile (id, name, email, phone) VALUES(?, ?, ?, ?)";
            jdbcTemplate.update(createSql, userProfile.getId(), userProfile.getName(), userProfile.getEmail(), userProfile.getPhone());
            userProfiles.add(userProfile);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(UserProfile userProfile) {
        try {
            String sql = "SELECT name FROM userprofile WHERE id = ?";
            if (jdbcTemplate.query(sql, new Object[]{userProfile.getId()}, (rs, rowNum) -> rs.getString("name")).size() == 0)
                return false;
            String updateSql = "UPDATE userprofile SET name = ?, email = ?, phone = ? where id = ?";
            jdbcTemplate.update(updateSql, userProfile.getName(), userProfile.getEmail(), userProfile.getPhone(), userProfile.getId());
            userProfiles.add(userProfile);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
