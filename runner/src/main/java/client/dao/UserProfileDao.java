package client.dao;

import entity.UserProfile;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public interface UserProfileDao {
    boolean create(UserProfile userProfile);

    boolean update(UserProfile userProfile);
    UserProfile findById(long userId);
    List<UserProfile> findAll();
}
