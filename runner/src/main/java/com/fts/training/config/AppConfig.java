package com.fts.training.config;

import com.fts.icb.common.cache.CacheHelper;
import com.fts.icb.common.genuid.GenUID;
import com.fts.icb.common.genuid.impl.GenUIDImpl;
import com.fts.training.buz.MessageProducer;
import com.fts.training.buz.UserProfileBuz;
import com.fts.training.buz.impl.UserProfileBuzImpl;
import com.fts.training.client.sdk.Client;
import com.fts.training.client.sdk.impl.ClientImpl;
import com.fts.training.dao.UserProfileDao;
import com.fts.training.dao.impl.UserProfileDaoImpl;
import com.fts.training.entity.UserProfile;
import com.fts.training.server.handler.UserProfileHandler;
import com.fts.training.service.UserProfileService;
import com.fts.training.service.impl.UserProfileServiceImpl;
import com.inspire.lab.config.KafkaConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:application.properties")
@Import({SpringJdbcConfig.class, KafkaConfig.class})
@ComponentScan("com.fts.training.dto")
public class AppConfig {

    @Value("${genuid.db.url}")
    String genUidDbUrl;
    @Value("${genuid.db.username}")
    String genUidDbUserName;
    @Value("${genuid.db.password}")
    String genUidDbPassword;

    @Bean
    @Scope("prototype")
    UserProfileHandler serviceHandler() {
        return new UserProfileHandler();
    }

    @Bean
    List<UserProfile> userProfile() {
        return new ArrayList<>();
    }

    @Bean
    UserProfileDao userProfileDao() {
        return new UserProfileDaoImpl();
    }

    @Bean
    UserProfileService userProfileService() {
        return new UserProfileServiceImpl();
    }

    @Bean
    UserProfileBuz userProfileBuz() {
        return new UserProfileBuzImpl();
    }

    @Bean
    MessageProducer messageProducer() {
        return new MessageProducer();
    }

    @Bean
    GenUID genUID() throws SQLException {
        return new GenUIDImpl(genUidDbUrl, genUidDbUserName, genUidDbPassword);
    }

    @Bean
    Client client() {
        return new ClientImpl("localhost", 8090, 821300826210307L, "key_821300826210307", false);
    }

    @Bean
    public CacheHelper cacheHelper() {
        return new CacheHelper("redis://127.0.0.1:6379", "");
    }


}
