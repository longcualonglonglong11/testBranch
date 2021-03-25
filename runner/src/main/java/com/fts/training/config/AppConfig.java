package com.fts.training.config;

import com.fts.training.dao.UserProfileDao;
import com.fts.training.dao.impl.UserProfileDaoImpl;
import com.fts.training.entity.UserProfile;
import com.fts.training.server.handler.ServiceHandler;
import com.fts.training.service.UserProfileService;
import com.fts.training.service.impl.UserProfileServiceImpl;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.fts.training")
public class AppConfig {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    ServiceHandler serviceHandler() {
        return new ServiceHandler();
    }

    @Bean
    List<UserProfile> userProfile() {
        return new ArrayList<>();
    }

}
