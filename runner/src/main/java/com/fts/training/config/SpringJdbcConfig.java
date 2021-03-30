package com.fts.training.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@PropertySource("classpath:application.properties")
@Configuration
public class SpringJdbcConfig {


    @Bean
    public DataSource dataSource(@Value("${spring.jdbc.url:jdbc:postgresql://0.0.0.0:5432/rainbow_database}")
                                         String url,
                                 @Value("${spring.jdbc.username:unicorn_user}")
                                         String username,
                                 @Value("${spring.jdbc.password:magical_password}")
                                         String password
    ) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
