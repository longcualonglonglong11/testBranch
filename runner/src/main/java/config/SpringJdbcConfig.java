package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
@Configuration
public class SpringJdbcConfig {
    @Bean
    public DataSource setDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://0.0.0.0:5432/rainbow_database");
        dataSource.setUsername("unicorn_user");
        dataSource.setPassword("magical_password");
        return dataSource;
    }
}
